package org.example.project.ktor_client.dtos

import kotlinx.serialization.Serializable


@Serializable
data class SignUpRequest(
    val username: String,
    val password: String
)