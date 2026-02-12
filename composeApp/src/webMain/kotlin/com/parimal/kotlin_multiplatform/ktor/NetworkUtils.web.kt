package com.parimal.kotlin_multiplatform.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual fun getHttpClientEngine(): HttpClientEngine = Js.create()