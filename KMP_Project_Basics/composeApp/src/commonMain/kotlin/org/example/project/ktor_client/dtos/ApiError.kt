package org.example.project.ktor_client.dtos

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class ApiError (
    val message: String,
    @Contextual
    val status: HttpStatusCode,
    val subErrors: List<String>
)