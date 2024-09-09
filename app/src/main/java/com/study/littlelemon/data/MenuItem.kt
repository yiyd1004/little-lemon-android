package com.study.littlelemon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItem (
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("category")
    val category: String,
    @SerialName("image")
    val image: String,
)
