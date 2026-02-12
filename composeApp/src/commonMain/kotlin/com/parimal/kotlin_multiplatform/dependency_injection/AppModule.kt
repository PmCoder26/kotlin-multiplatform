package com.parimal.kotlin_multiplatform.dependency_injection


import com.parimal.kotlin_multiplatform.ktor.ApiClient
import com.parimal.kotlin_multiplatform.ktor.getHttpClientEngine
import com.parimal.kotlin_multiplatform.viewmodels.StudentViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val appModule = module {

    viewModelOf(::StudentViewModel)

    single {
        getHttpClientEngine()
    }

    single {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    singleOf(::ApiClient)

}


fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {

    appDeclaration()

    modules(appModule)

}