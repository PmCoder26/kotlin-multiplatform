package org.example.project.ktor_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.project.ktor_client.dtos.Signup
import org.example.project.ktor_client.dtos.SignupResponse

class AuthClient(
    private val httpClient: HttpClient
) {

    suspend fun signup(user: Signup): SignupResponse? {
        val response = try{
            httpClient.post(
                urlString = "http://192.168.246.29:8080/auth/signup",
            ){
                setBody(user)
            }.body<SignupResponse>()
        } catch (e: Exception){
            println(e.message)
            return null
        }
        return response
    }

}