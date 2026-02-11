package com.parimal.kotlin_multiplatform

import com.parimal.kotlin_multiplatform.dependency_injection.initKoin

object KoinStarter {
    fun start() {
        initKoin { }
    }
}