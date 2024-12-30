package org.example.project.ktor_client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.ktor_client.dtos.LoginRequest
import org.example.project.ktor_client.dtos.Person
import org.example.project.ktor_client.dtos.SignUpRequest


@Composable
fun KtorScreen(
    httpClient: HttpClient = HttpClient(),
    tokenManager: TokenManager? = null
) {
    val ktorNavCon = rememberNavController()
    val personClient = remember {
        PersonClient(httpClient)
    }
    val authClient = remember {
        AuthClient(httpClient, tokenManager)
    }

    NavHost(navController = ktorNavCon, startDestination = "KtorHomeScreen") {
        composable("KtorHomeScreen") {
            var route by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                launch {
                    if(tokenManager?.tokensCheck() == true) {
                        route = true
                    }
                }
            }
            if(route) {
                PeopleDetailsScreen(personClient)
            }
            else {
                KtorHomeScreen(ktorNavCon)
            }
        }

        composable("SignUpScreen") {
            SignUpScreen(authClient, ktorNavCon)
        }

        composable("LoginScreen") {
            LoginScreen(authClient, ktorNavCon)
        }

        composable("PeopleDetailsScreen") {
            PeopleDetailsScreen(personClient, authClient, ktorNavCon)
        }
    }

}

@Composable
fun KtorHomeScreen(
    ktorNavCon: NavHostController = rememberNavController()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            IconButton(
                onClick = {
                    ktorNavCon.navigate("SignUpScreen")
                }
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "signup",
                        modifier = Modifier.size(150.dp),
                        tint = Color.Blue
                    )
                    Text("Signup",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            IconButton(
                onClick = {
                    ktorNavCon.navigate("LoginScreen")
                }
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "login",
                        modifier = Modifier.size(150.dp),
                        tint = Color.Blue
                    )
                    Text("Login",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    authClient: AuthClient = AuthClient(HttpClient(),  null),
    ktorNavCon: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var signupSuccess by remember {
            mutableStateOf(false)
        }

        if(signupSuccess) {
            ktorNavCon.navigate("KtorHomeScreen"){
                popUpTo("SignUpScreen"){
                    inclusive = true
                }
            }
        }

        OutlinedTextField(
            value = username,
            onValueChange = { value ->
                username = value
            },
            placeholder = {
                Text("Username")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { value ->
                password = value
            },
            placeholder = {
                Text("Password")
            }
        )

        Button(
            onClick = {
                scope.launch {
                    withContext(Dispatchers.IO){
                        signupSuccess = authClient.signUp(
                            SignUpRequest(username, password)
                        )
                    }
                }
            }
        ) {
            Text("SignUp")
        }
    }
}

@Composable
fun LoginScreen(
    authClient: AuthClient? = null,
    ktorNavCon: NavHostController = rememberNavController(),
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var isLoginSuccess by remember {
            mutableStateOf(false)
        }

        if(isLoginSuccess) {
            ktorNavCon.navigate("PeopleDetailsScreen")
        }

        OutlinedTextField(
            value = username,
            onValueChange = { value ->
                username = value
            },
            placeholder = {
                Text("Username")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { value ->
                password = value
            },
            placeholder = {
                Text("Password")
            }
        )

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val auth = launch {
                        authClient?.login(
                            LoginRequest(username, password)
                        )
                    }
                    auth.join()
                    launch {
                        if(authClient?.checkTokens() == true) {
                            isLoginSuccess = true
                        }
                        else {
                            println("Login failed!")
                        }
                    }
                }
            }
        ) {
            Text("Log in")
        }
    }
}

@Composable
fun PeopleDetailsScreen(
    personClient: PersonClient = PersonClient(HttpClient()),
    authClient: AuthClient = AuthClient(),
    ktorNavCon: NavHostController = rememberNavController()
) {
    var addPerson by remember {
        mutableStateOf(false)
    }
    var personList by remember {
        mutableStateOf(listOf<Person>())
    }
    var flag by remember {
        mutableStateOf(false)
    }
    var isLogout by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    if(isLogout) {
        ktorNavCon.navigate("KtorHomeScreen"){
            popUpTo("KtorHomeScreen"){
                inclusive = false
            }
        }
    }

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
                    horizontalArrangement = Arrangement.SpaceBetween,
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

                    IconButton(
                        onClick = {
                            scope.launch {
                                authClient.logout()
                                isLogout = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "logout",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(fraction = 0.85f)
                                .height(70.dp)
                                .padding(start = 30.dp),
                        ) {
                            Canvas(
                                onDraw = {
                                    drawRoundRect(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(0x9C6790EE),
                                                Color(0x7C285BB4)
                                            )
                                        )
                                    )
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(person.name,
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(person.age.toString(),
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        personClient.removePerson(person.id)
                                    }
                                    withContext(Dispatchers.Main) {
                                        flag = !flag
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete person",
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xB5285BB4)
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}