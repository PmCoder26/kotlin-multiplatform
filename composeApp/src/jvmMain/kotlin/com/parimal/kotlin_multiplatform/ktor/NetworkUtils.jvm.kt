package com.parimal.kotlin_multiplatform.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

actual fun getHttpClientEngine(): HttpClientEngine = CIO.create()