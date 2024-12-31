package org.example.project.ktor_client

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.ktor_client.dtos.LoginResponse

class TokenManager(
    private var dataStore: DataStore<Preferences>
) : ViewModel() {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    private var accessToken = MutableStateFlow<String?>(null)
    private var refreshToken = MutableStateFlow<String?>(null)
    private var _state = MutableStateFlow(TokenState(accessToken.value, refreshToken.value))
    val tokenState = combine(accessToken, refreshToken, _state){ accessToken, refreshToken, _state ->
        _state.copy(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TokenState(accessToken.value, refreshToken.value))
    val tokenState2 = _state


    init {
        viewModelScope.launch {
            dataStore.data.collectLatest { pref ->
                val access = pref[ACCESS_TOKEN_KEY]
                val refresh = pref[REFRESH_TOKEN_KEY]

                accessToken.update {
                    access
                }
                refreshToken.update {
                    refresh
                }
                _state.update {
                    println("Tokens updated")
                    TokenState(accessToken.value, refreshToken.value)
                }
            }

        }
    }

    suspend fun saveTokens(data: LoginResponse) {
        dataStore.edit { pref ->
            pref[ACCESS_TOKEN_KEY] = data.accessToken
            pref[REFRESH_TOKEN_KEY] = data.refreshToken
        }
    }

    fun tokensCheck(): Boolean {
        return accessToken.value != null && refreshToken.value != null
    }

    suspend fun clearTokens() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }

}