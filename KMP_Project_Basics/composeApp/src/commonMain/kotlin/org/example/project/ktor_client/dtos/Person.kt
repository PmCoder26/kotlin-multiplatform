package org.example.project.ktor_client.dtos

import kotlinx.serialization.Serializable


@Serializable
data class Person(
    val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: String
)