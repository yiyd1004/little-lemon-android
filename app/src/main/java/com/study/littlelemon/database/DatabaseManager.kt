package com.study.littlelemon.database

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface DatabaseManager {
    fun getUser(): String
    fun setUser(firstName: String, lastName: String, email: String)
    fun isUserLogIn(): Boolean
    fun setUserLogIn(isLogged: Boolean)
    suspend fun getMenu(): List<MenuEntity>
    suspend fun insertMenu(items: List<MenuEntity>)
}

class DatabaseManagerImpl(
    private val sharedPref: SharedPreferences,
    private val database: MenuDao
): DatabaseManager {

    private val USER: String = "user";
    private val USER_LOGIN_STATUS = "is_user_log_in"

    override fun getUser(): String {
        return sharedPref.getString(USER, "").orEmpty()
    }

    override fun setUser(firstName: String, lastName: String, email: String) {
        val userStr: String = getUser();

        val user: UserEntity = if (userStr.isEmpty()) {
            UserEntity("", "", "")
        } else {
            Json.decodeFromString<UserEntity>(userStr)
        }

        val isLoggedOut: Boolean = isUserLogIn()

        if (firstName.isNotBlank() || !isLoggedOut) {
            user.firstName = firstName
        }

        if (lastName.isNotBlank() || !isLoggedOut) {
            user.lastName = lastName
        }

        if (email.isNotBlank() || !isLoggedOut) {
            user.email = email
        }

        val updatedStr = Json.encodeToString(user)
        sharedPref.edit(commit = true) {
            putString(USER,  updatedStr)
        }
    }

    override fun isUserLogIn(): Boolean {
        return sharedPref.getBoolean(USER_LOGIN_STATUS, false)
    }

    override fun setUserLogIn(isLogged: Boolean) {
        sharedPref.edit(commit = true) {
            putBoolean(USER_LOGIN_STATUS, isLogged)
        }
    }

    override suspend fun getMenu(): List<MenuEntity> {
        return database.getAll()
    }

    override suspend fun insertMenu(items: List<MenuEntity>) {
        database.insertAll(items)
    }
}