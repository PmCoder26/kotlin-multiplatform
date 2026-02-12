package com.parimal.kotlin_multiplatform.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiClient(
    private val httpClient: HttpClient
) {

    suspend fun getTodos(): List<Todo> {
        val response = try {
            httpClient.get(urlString = "https://jsonplaceholder.typicode.com/todos")
                .body<List<Todo>>()
        } catch (e: Exception) {
            println("Ktor error: ${e.message}")
            null
        }
        println(response)

        return response ?: emptyList()
    }

}