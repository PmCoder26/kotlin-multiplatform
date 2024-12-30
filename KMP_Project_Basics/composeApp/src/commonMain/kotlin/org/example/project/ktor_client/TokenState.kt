package org.example.project.ktor_client

data class TokenState(
    val accessToken: String?,
    val refreshToken: String?
)
