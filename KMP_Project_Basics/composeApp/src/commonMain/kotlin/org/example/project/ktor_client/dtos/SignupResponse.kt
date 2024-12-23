package org.example.project.ktor_client.dtos

import kotlinx.serialization.Serializable


@Serializable
data class SignupResponse (
    val id: Long,
    val username: String
)