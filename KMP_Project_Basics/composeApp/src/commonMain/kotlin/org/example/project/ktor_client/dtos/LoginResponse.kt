package org.example.project.ktor_client.dtos

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)