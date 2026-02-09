package com.parimal.kotlin_multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform