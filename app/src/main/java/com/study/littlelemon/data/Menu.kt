package com.study.littlelemon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    @SerialName("menu")
    val menu: List<MenuItem>
)