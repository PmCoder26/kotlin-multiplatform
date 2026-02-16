package com.parimal.kotlin_multiplatform.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(productPath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath( produceFile = { productPath().toPath() } )

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"