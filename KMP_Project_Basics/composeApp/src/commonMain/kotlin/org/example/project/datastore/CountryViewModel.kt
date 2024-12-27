package org.example.project.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CountryViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val COUNRY_NAME_KEY = stringPreferencesKey("country_name")
    val countryState = dataStore
        .data
        .mapLatest { pref ->
            pref[COUNRY_NAME_KEY]
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    fun saveOrUpdateCountry(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { pref ->
                pref[COUNRY_NAME_KEY] = name
            }
        }
    }

    fun deleteCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { pref ->
                pref.remove(COUNRY_NAME_KEY)
            }
        }
    }



}