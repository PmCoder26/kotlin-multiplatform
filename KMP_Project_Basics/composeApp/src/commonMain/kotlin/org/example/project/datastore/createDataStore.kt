package org.example.project.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

//   Preferences is a type of data container that allows you to store simple key-value pairs.
//   It provides a way to save data such as settings, flags, or configuration values that are
//   not complex enough to require a full database like Room.

fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }        // .toPath() returns a Path object.
    )
}


// 'internal' insures that this below constant can only be accessed in the composeApp directory.
internal val DATASTORE_FILE_NAME = "pref.preferences_pb"             // syntax of this filename+extension: file_name.preferences_pb