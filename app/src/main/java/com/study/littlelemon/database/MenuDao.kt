package com.study.littlelemon.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM Menu")
    suspend fun getAll(): List<MenuEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuEntity>)
}