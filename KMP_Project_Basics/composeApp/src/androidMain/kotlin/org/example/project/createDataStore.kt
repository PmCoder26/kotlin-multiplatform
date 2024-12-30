package org.example.project

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.project.datastore.DATASTORE_FILE_NAME

fun createDataStore(context: Context): DataStore<Preferences> {
    return org.example.project.datastore.createDataStore {
        context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
    }
}