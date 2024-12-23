package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.database.room_database.User
import org.example.project.database.room_database.UserViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun UserHomeScreen(viewModel: UserViewModel? = null) {
    viewModel?.let { viewModel ->
        val isAddUser by viewModel.isAddedUser.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color(0xAB5A5AD7))
                        .height(50.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Users",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        ),
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            viewModel.alterFlag()
                        },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add user",
                            tint = Color(0xAB5A5AD7),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(!isAddUser) {
                    Spacer(modifier = Modifier.height(10.dp))
                    ShowAllUsersComposable(viewModel)
                }

                AnimatedVisibility(isAddUser) {
                    AddUserComposable(viewModel)
                }
            }
        }
    }
}


@Preview
@Composable
fun ShowAllUsersComposable(viewModel: UserViewModel? = null) {
    viewModel?.let { viewModel ->
        val userList by viewModel.state.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userList.isEmpty()) {
                item {
                    Text(
                        text = "No users to display!",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
            } else {
                items(userList) { user ->
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 70.dp)
                                .padding(start = 20.dp, end = 20.dp),
                            shape = RoundedCornerShape(7.dp),
                            elevation = 7.dp
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Name: ${user.name}",
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    )
                                )
                                Text(
                                    text = "Age: ${user.age}",
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                viewModel.removeUser(user)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Delete, "remove user")
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AddUserComposable(viewModel: UserViewModel? = null) {
    viewModel?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by remember {
                mutableStateOf("")
            }
            var age by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = name,
                onValueChange = { value ->
                    name = value
                },
                placeholder = {
                    Text("Name")
                }
            )
            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { value ->
                    age = value
                },
                placeholder = {
                    Text("Age")
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        viewModel.alterFlag()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xAB5A5AD7))
                ) {
                    Text("Cancel", color = Color.White)
                }
                Button(
                    onClick = {
                        if (age.isNotBlank() && name.isNotBlank()) {
                            viewModel.addUser(User(name = name, age = age.toInt()))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xAB5A5AD7))
                ) {
                    Text("Add", color = Color.White)
                }
            }
        }
    }
}

