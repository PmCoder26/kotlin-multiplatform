package org.example.project.ktor_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.ktor_client.dtos.ApiResponse
import org.example.project.ktor_client.dtos.Message
import org.example.project.ktor_client.dtos.Person

class PersonClient(
    private val httpClient: HttpClient
) {

    suspend fun getAllPeople(): List<Person> {
        try {
            val response = httpClient.get(
                urlString = "http://$HOST_URL/persons/getAllPeople",
            ) {
                contentType(ContentType.Application.Json)
            }.body<ApiResponse<List<Person>>>()
            response.data?.let { data ->
                println("Response: ${data}")
                return data
            }
            response.error?.let { error ->
                println("All people error: ${error.message}")
            }
        } catch (e: Exception){
            println("Error: " + e.message)
        }
        return emptyList()
    }

    suspend fun addPerson(person: Person) {
        try{
            httpClient.post(
                urlString = "http://$HOST_URL/persons/addPerson"
            ){
                contentType(ContentType.Application.Json)
                setBody(person)
            }
        } catch (e: Exception) {
            print("Person adding error: " + e.message)
        }
    }

    suspend fun removePerson(id: Long) {
        try{
            val response = httpClient.delete(
                urlString = "http://$HOST_URL/persons/removePerson/${id}"
            ).body<ApiResponse<Message>>()
            response.data?.let { data ->
                val message: Message = data
                println("Api Response: $message")
            }
            response.error?.let{ error ->
                println("Api Response error: ${error.message}")
            }
        } catch(e: Exception) {
            println("Person remove error: " + e.message)
        }
    }

}