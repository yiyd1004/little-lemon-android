package com.study.littlelemon.database

import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    var firstName: String,
    var lastName: String,
    var email: String,
)
