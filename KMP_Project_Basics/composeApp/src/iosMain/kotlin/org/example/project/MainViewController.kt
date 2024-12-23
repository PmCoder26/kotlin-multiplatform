package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.example.project.database.getDao
import org.example.project.ktor_client.createHttpClient

fun MainViewController() = ComposeUIViewController {
    val httpclient = remember {
        createHttpClient(Darwin.create())
    }
    App(getDao(), httpclient)
}