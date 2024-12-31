package org.example.project.ktor_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.ktor_client.dtos.ApiResponse
import org.example.project.ktor_client.dtos.LoginRequest
import org.example.project.ktor_client.dtos.LoginResponse
import org.example.project.ktor_client.dtos.SignUpRequest
import org.example.project.ktor_client.dtos.SignUpResponse
import org.example.project.ktor_client.dtos.Tokens

class AuthClient(
    private val httpClient: HttpClient? = null,
    private val tokenManager: TokenManager? = null,
) {

    suspend fun signUp(signUpRequest: SignUpRequest): Boolean{
        val response = try{
            httpClient!!.post(
                urlString = "http://$HOST_URL/sign/signup",
            ){
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }.body<ApiResponse<SignUpResponse>>()
        } catch (e: Exception){
            println("SignUp error: ${e.message}")
            null
        }
        response?.data?.let { data ->
            println("SignUp response: ${data}")
            return true
        }
        response?.error?.let { error ->
            println("SignUp response error: ${error.message}")
        }
        return false
    }

    suspend fun login(loginRequest: LoginRequest) {
        val response = try{
            httpClient!!.post(
                urlString = "http://$HOST_URL/auth/login"
            ){
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }.body<ApiResponse<LoginResponse>>()
        } catch (e: Exception) {
            println("Login error: ${e.message}")
            null
        }
        response?.data?.let { data ->
            tokenManager?.saveTokens(data)
            updateClient()
            println("Login response data: ${data}")
        }
        response?.error?.let { error ->
            println("Login response error: ${error.message}")
        }
    }

    suspend fun refreshTokens() {
        tokenManager?.let { tokenManager ->
            val tokenState = tokenManager.tokenState2.value
            val response = try {
                httpClient!!.post(
                    urlString = "http://$HOST_URL/auth/refresh-tokens"
                ) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        Tokens(
                            accessToken = tokenState.accessToken!!,
                            refreshToken = tokenState.refreshToken!!
                        )
                    )
                    headers {
                        remove("Authorization")
                    }
                }.body<ApiResponse<Tokens>>()
            } catch (e: Exception) {
                println("Token refresh error: ${e.message}")
                null
            }
            response?.data?.let { data ->
                println("Refreshing tokens response data: $data")
                tokenManager.saveTokens(
                    LoginResponse(
                        accessToken = data.accessToken,
                        refreshToken = data.refreshToken
                    )
                )

                updateClient()
            }
            response?.error?.let { error ->
                println("Token refresh response error: ${error.message}")
            }
        }
    }

    fun checkTokens(): Boolean {
        val tokenState = tokenManager?.tokenState2?.value
        return tokenState?.accessToken != null && tokenState.refreshToken != null
    }

    private fun updateClient() {
        httpClient!!.plugin(Auth).bearer {
            loadTokens {
                val tokenState = tokenManager!!.tokenState2.value
                if(tokenManager.tokensCheck()) {
                    BearerTokens(
                        accessToken = tokenState.accessToken.toString(),
                        refreshToken = tokenState.refreshToken.toString()
                    )
                }
                else{
                    null
                }
            }
        }
    }

    suspend fun logout() {
        tokenManager?.clearTokens()
    }


}