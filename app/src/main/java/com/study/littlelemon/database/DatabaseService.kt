package com.study.littlelemon.database

import android.content.Context
import androidx.room.Room

object DatabaseService {
    fun build(context: Context): LittleLemonDB {
        return Room.databaseBuilder(
            context,
            LittleLemonDB::class.java,
            "little_lemon_database"
        ).build()
    }
}