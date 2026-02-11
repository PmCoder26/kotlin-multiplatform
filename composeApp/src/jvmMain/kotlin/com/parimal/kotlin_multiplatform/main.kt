package com.parimal.kotlin_multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.parimal.kotlin_multiplatform.dependency_injection.initKoin

fun main() {

    initKoin {  }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kotlinmultiplatform",
        ) {
            App()
        }
    }
}