package com.study.littlelemon.navigation

enum class Destinations {
    HOME,
    PROFILE,
    ON_BOARDING;

    val route: String
        get() = name.lowercase()
}