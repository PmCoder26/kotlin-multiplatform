package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.okhttp.OkHttp
import org.example.project.database.getDao
import org.example.project.datastore.DATASTORE_FILE_NAME
import org.example.project.datastore.createDataStore
import org.example.project.ktor_client.createHttpClient


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        val httpclient = remember {
            createHttpClient(OkHttp.create())
        }
        val datastore = remember {
            createDataStore { DATASTORE_FILE_NAME }
        }

        App(getDao(), httpclient, datastore)

    }
}