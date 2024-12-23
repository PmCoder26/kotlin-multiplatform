package org.example.project.ktor_client

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import org.example.project.ktor_client.dtos.Person


@Composable
fun KtorScreen(httpclient: HttpClient = HttpClient()) {
    val personClient = remember {
        PersonClient(httpclient)
    }
    var addPerson by remember {
        mutableStateOf(false)
    }
    var personList by remember {
        mutableStateOf(listOf<Person>())
    }
    var flag by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    AnimatedVisibility(addPerson) {
        var name by remember {
            mutableStateOf("")
        }
        var age by remember {
            mutableStateOf("")
        }
        var gender by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedTextField(
                value = name,
                onValueChange = { value ->
                    name = value
                },
                placeholder = {
                    Text("Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                )
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = gender,
                onValueChange = { value ->
                    gender = value
                },
                placeholder = {
                    Text("Gender")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { value ->
                    age = value
                },
                placeholder = {
                    Text("Age")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        addPerson = !addPerson
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF285BB4))
                ) {
                    Text("Cancel", color = Color.White)
                }

                Button(
                    onClick = {
                        if (name.isNotBlank() && age.isNotBlank() && gender.isNotBlank()) {
                            val person = Person(name = name, age = age.toInt(), gender = gender)
                            scope.launch {
                                personClient.addPerson(person)
                                flag = !flag
                                addPerson = !addPerson
                            }
                        } else {
                            println("Invalid data input!!")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF285BB4))
                ) {
                    Text("Add", color = Color.White)
                }
            }
        }
    }

    AnimatedVisibility(!addPerson) {
        LaunchedEffect(flag) {
            personList = personClient.getAllPeople()
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(70.dp)
                        .background(Color(0xB5285BB4)),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "People",
                        style = TextStyle(
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
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
                ){
                    FloatingActionButton(
                        onClick = {
                            addPerson = !addPerson
                        },
                        modifier = Modifier.padding(20.dp),
                        backgroundColor = Color(0xFF0E6BC2),
                    ) {
                        Icon(Icons.Default.Add, "add person", tint = Color.White)
                    }
                }
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(personList) { person ->
                    Text(person.toString())
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}