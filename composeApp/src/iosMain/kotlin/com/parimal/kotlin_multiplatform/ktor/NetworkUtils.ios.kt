package com.parimal.kotlin_multiplatform.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClientEngine(): HttpClientEngine = Darwin.create()