package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import org.example.project.database.room_database.UserDao
import org.example.project.datastore.DataStoreScreen
import org.example.project.ktor_client.KtorScreen
import org.example.project.ktor_client.TokenManager
import org.example.project.room_database.RoomDatabaseScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(dao: UserDao, httpclient: HttpClient = HttpClient(), dataStore: DataStore<Preferences>) {

    val routeList = arrayListOf(
        NavRoute("Room", "RoomDatabaseScreen"),
        NavRoute("Ktor client", "KtorScreen"),
        NavRoute("DataStore", "DataStoreScreen"),
    )

    MaterialTheme {
        val navCon = rememberNavController()

        NavHost(navController = navCon, startDestination = "HomeScreen"){
            composable("HomeScreen") {
                HomeScreen(routeList, navCon)
            }

            composable("RoomDatabaseScreen") {
                RoomDatabaseScreen(dao)
            }

            composable("KtorScreen") {
                val tokenManager = remember {
                    TokenManager(dataStore, httpclient)
                }
                KtorScreen(httpclient, tokenManager)
            }

            composable("DataStoreScreen") {
                DataStoreScreen(dataStore)
            }
        }
    }
}


@Preview
@Composable
fun HomeScreen(routeList: ArrayList<NavRoute>, navCon: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(routeList){ route ->
            RouteComponent(route, navCon)
        }
    }

}


@Composable
fun RouteComponent(route: NavRoute, navCon: NavHostController){
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .height(50.dp)
            .clickable {
                navCon.navigate(route.destination)
            },
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(route.text, fontSize = 18.sp)
        }
    }
}
