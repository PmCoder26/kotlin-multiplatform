package org.example.project.ktor_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.ktor_client.dtos.ApiError
import org.example.project.ktor_client.dtos.ApiResponse
import org.example.project.ktor_client.dtos.Message
import org.example.project.ktor_client.dtos.Person

class PersonClient(
    private val httpClient: HttpClient,
    private val authClient: AuthClient?
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
                handleTokensExpired(error)
                println("All people response error: ${error.message}")
            }
        } catch (e: Exception){
            println("Error: " + e.message)
        }
        return emptyList()
    }

    suspend fun addPerson(person: Person) {
        try{
            val response = httpClient.post(
                urlString = "http://$HOST_URL/persons/addPerson"
            ){
                contentType(ContentType.Application.Json)
                setBody(person)
            }.body<ApiResponse<Person>>()
            response.data?.let { data ->
                println("Add person response data: ${data}")
            }
            response.error?.let { error ->
                handleTokensExpired(error)
                println("Add person response error: ${error.message}")
            }
        } catch (e: Exception) {
            print("Add person error: " + e.message)
        }
    }

    suspend fun removePerson(id: Long) {
        try{
            val response = httpClient.delete(
                urlString = "http://$HOST_URL/persons/removePerson/${id}"
            ).body<ApiResponse<Message>>()
            response.data?.let { data ->
                println("Remove person response data: $data")
            }
            response.error?.let{ error ->
                handleTokensExpired(error)
                println("Remove person response error: ${error.message}")
            }
        } catch(e: Exception) {
            println("Person remove error: " + e.message)
        }
    }

    private suspend fun handleTokensExpired(error: ApiError) {
        if(error.status == "UNAUTHORIZED") {
            println("Inside the handleTokensExpired")
            authClient?.refreshTokens()
        }
    }

}