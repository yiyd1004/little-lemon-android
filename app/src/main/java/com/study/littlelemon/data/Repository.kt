package com.study.littlelemon.data

import android.util.Log
import com.study.littlelemon.database.DatabaseManager
import com.study.littlelemon.database.MenuEntity
import com.study.littlelemon.database.UserEntity
import com.study.littlelemon.network.NetworkDataManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface Repository {
    suspend fun getMenu(): List<MenuEntity>
    fun isUserLogIn(): Boolean
    fun setUserLogIn(isLogIn: Boolean)
    fun getUserData(): UserEntity
    fun setUserData(firstname: String, lastName: String, email: String)
}

class RepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val networkDataManager: NetworkDataManager,
    private val databaseManager: DatabaseManager
): Repository {

    override suspend fun getMenu(): List<MenuEntity> {
        return withContext(dispatcher) {
            runCatching {
                networkDataManager.getMenu()
            }.onFailure {
                Log.e("Repository", "Unable to get menu from server: ", it)
            }.onSuccess { it ->
                databaseManager.insertMenu(
                    it.map { menu ->
                        MenuEntity(
                            title = menu.title,
                            description = menu.description,
                            price = menu.price,
                            category = menu.category,
                            image = menu.image
                        )
                    }
                )
            }

            databaseManager.getMenu()
        }
    }

    override fun isUserLogIn(): Boolean {
        return databaseManager.isUserLogIn()
    }

    override fun setUserLogIn(isLogIn: Boolean) {
        databaseManager.setUserLogIn(isLogIn)

        if (!isLogIn) {
            setUserData("", "", "")
        }
    }

    override fun getUserData(): UserEntity {
        return Json.decodeFromString<UserEntity>(databaseManager.getUser())
    }

    override fun setUserData(firstname: String, lastName: String, email: String) {
        databaseManager.setUser(firstname, lastName, email)
    }
}