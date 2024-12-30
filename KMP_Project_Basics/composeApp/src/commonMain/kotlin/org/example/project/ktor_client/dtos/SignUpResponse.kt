package org.example.project.ktor_client.dtos

import kotlinx.serialization.Serializable


@Serializable
data class SignUpResponse (
    val id: Long,
    val username: String
)