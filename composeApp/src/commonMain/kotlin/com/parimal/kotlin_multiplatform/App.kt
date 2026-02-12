package com.parimal.kotlin_multiplatform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.parimal.kotlin_multiplatform.ktor.ApiClient
import com.parimal.kotlin_multiplatform.ktor.Todo
import com.parimal.kotlin_multiplatform.ktor.TodosScreen
import com.parimal.kotlin_multiplatform.viewmodels.StudentViewModel
import com.parimal.kotlin_multiplatform.viewmodels.ViewModelScreen
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        val globalViewModelOwner = remember {
            object : ViewModelStoreOwner {
                override val viewModelStore = ViewModelStore()
            }
        }

        NavHost(navController = navController, startDestination = "RouteGallery") {

            composable("RouteGallery") {
                RouteGallery(navController)
            }

            composable("ViewModel") {
                val viewModel = koinViewModel<StudentViewModel>(viewModelStoreOwner = globalViewModelOwner)

                ViewModelScreen(
                    studentList = viewModel.studentList,
                    onAddNewStudent = {
                        viewModel.addStudent(it)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("Todos") {
                val apiClient = koinInject<ApiClient>()

                val todosList by produceState(initialValue = emptyList<Todo>()) {
                    value = apiClient.getTodos()
                }

                TodosScreen(
                    todosList = todosList,
                    onBackClicked = { navController.popBackStack() }
                )
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = Devices.DESKTOP)
@Composable
fun RouteGallery(navController: NavHostController = rememberNavController()) {

    val routeList = listOf("ViewModel", "Todos")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("KMP Routes")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xDF1D51BF),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {

            items(routeList) { route ->
                RouteItem(route, navController)
            }

        }

    }

}

@Composable
fun RouteItem(route: String, navController: NavHostController) {

    ElevatedCard(
        modifier = Modifier
            .size(200.dp, 50.dp)
            .padding(7.dp)
            .clickable {
                navController.navigate(route)
            },
        shape = CardDefaults.elevatedShape,
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0x8D90A8DB)),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = route,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
    
}