package com.parimal.kotlin_multiplatform.datastore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, device = Devices.DESKTOP)
@Composable
fun CounterScreenPreview() {
    CounterScreen(0, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    counter: Int,
    onCounterIncrement: () -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Counter in DataStore") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBackIos, "back")
                    }
                }
            )
        },
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text("Counter: $counter", modifier = Modifier.align(Alignment.Center))

            Button(
                onClick = onCounterIncrement,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 17.dp)
            ) { Icon(Icons.Default.Add, "counter increment") }

        }

    }

}