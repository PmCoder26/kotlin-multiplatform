package com.parimal.kotlin_multiplatform.viewmodels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.DESKTOP
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, device = DESKTOP)
@Composable
fun ViewModelScreenPreview() {
    ViewModelScreen(
        studentList = listOf(
        Student(1, "Parimal Matte", "BE", "A"),
        Student(1, "Rohit Gandhal", "BE", "A"),
    ),
        onAddNewStudent = { },
        onBackClick = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewModelScreen(
    studentList: List<Student>,
    onAddNewStudent: (Student) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Students")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "back", tint = Color.White) }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xDF1D51BF),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(17.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        onAddNewStudent(Student( if(studentList.isNotEmpty()) studentList.last().rollNo + 1 else 0))
                    }
                ) { Icon(Icons.Default.Add, "add a new student") }
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(studentList) { student ->
                StudentCard(student)
            }

        }

    }

}

@Composable
fun StudentCard(student: Student) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(11.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Roll No: ${student.rollNo}")
            Text("Name: ${ student.name }")
            Text("Class: ${ student.className }")
            Text("Division: ${ student.division }")
        }
    }

}