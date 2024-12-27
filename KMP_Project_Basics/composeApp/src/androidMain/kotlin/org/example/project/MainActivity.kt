package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import io.ktor.client.engine.okhttp.OkHttp
import org.example.project.database.getDao
import org.example.project.datastore.createDataStore
import org.example.project.ktor_client.createHttpClient

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val httpclient = remember {
                createHttpClient(OkHttp.create())
            }
            val datastore = remember {
                createDataStore(this@MainActivity)
            }

            App(getDao(this), httpclient, datastore)

        }
    }

}

