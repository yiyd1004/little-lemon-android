package com.study.littlelemon.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MenuEntity::class], version = 1)
abstract class LittleLemonDB: RoomDatabase() {
    abstract fun getMenuDao(): MenuDao
}