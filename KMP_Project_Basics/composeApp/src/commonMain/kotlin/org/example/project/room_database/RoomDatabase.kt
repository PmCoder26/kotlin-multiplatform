package org.example.project.room_database

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.project.UserHomeScreen
import org.example.project.database.room_database.UserDao
import org.example.project.database.room_database.UserViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun RoomDatabaseScreen(dao: UserDao){
    val viewModel by lazy{
        UserViewModel(dao)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        UserHomeScreen(viewModel)
    }

}