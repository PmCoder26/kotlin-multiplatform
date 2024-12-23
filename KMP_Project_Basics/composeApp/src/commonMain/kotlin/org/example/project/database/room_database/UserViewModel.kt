package org.example.project.database.room_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class UserViewModel(
    private val userDao: UserDao
) : ViewModel() {

    private val users = userDao.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val state = users

    var isAddedUser = MutableStateFlow(false)

    fun addUser(user: User){
        viewModelScope.launch {
            userDao.addUser(user)
            alterFlag()
        }
    }

    fun removeUser(user: User){
        viewModelScope.launch {
            userDao.removeUser(user)
        }
    }

    fun alterFlag(){
        viewModelScope.launch {
            isAddedUser.emit(!isAddedUser.value)
        }
    }

}