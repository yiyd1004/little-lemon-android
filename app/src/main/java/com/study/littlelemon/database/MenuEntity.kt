package com.study.littlelemon.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Menu")
data class MenuEntity(
    @PrimaryKey
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val image: String
)
