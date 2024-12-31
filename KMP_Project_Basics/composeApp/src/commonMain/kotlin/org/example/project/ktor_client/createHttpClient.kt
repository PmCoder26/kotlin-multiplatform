package org.example.project.ktor_client

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine, dataStore: DataStore<Preferences>): HttpClient {
    return HttpClient(engine) {
        install(Logging){
            level = LogLevel.ALL
        }
        install(ContentNegotiation){
            json(
                json = Json {
                    ignoreUnknownKeys = true        // helpful if unknown data comes, hence preventing from crashing.
                },
                contentType = ContentType.Application.Json
            )
        }
        install(Auth){

        }
    }
}