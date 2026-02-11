package com.parimal.kotlin_multiplatform

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.parimal.kotlin_multiplatform.dependency_injection.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    initKoin {  }

    ComposeViewport {
        App()
    }
}