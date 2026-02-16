package com.parimal.kotlin_multiplatform.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CounterViewModel(private val dataStore: DataStore<Preferences>) : ViewModel() {

    private val COUNTER_KEY = intPreferencesKey("counter_key")

    private val _counter = MutableStateFlow(0)

    val counter = _counter.asStateFlow()

    init {
        getCounter()
    }


    private fun getCounter() {
        viewModelScope.launch {
            _counter.value = dataStore.data.first()[COUNTER_KEY] ?: 0
        }
    }

    fun incrementCounter() {
        viewModelScope.launch(Dispatchers.Default) {
            dataStore.edit {
                it[COUNTER_KEY] = it[COUNTER_KEY]?.plus(1) ?: 1
                _counter.value = it[COUNTER_KEY]!!
            }
        }
    }

}