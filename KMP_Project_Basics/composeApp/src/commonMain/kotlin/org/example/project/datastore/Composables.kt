package org.example.project.datastore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


@Composable
fun DataStoreScreen(dataStore: DataStore<Preferences>) {
    val viewModel = remember {
        CountryViewModel(dataStore)
    }
    MyContent(viewModel)
}

@Composable
fun MyContent(viewModel: CountryViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var name by remember {
            mutableStateOf("")
        }
        val countryName by viewModel.countryState.collectAsState("Country Name")

        Text(countryName.toString())

        OutlinedTextField(
            value = name,
            onValueChange = { value ->
                name = value
            },
            placeholder = {
                Text("Country name")
            }
        )

        Button(
            onClick = {
                viewModel.saveOrUpdateCountry(name)
            }
        ){
            Text("Update")
        }

        Button(
            onClick = {
                viewModel.deleteCountry()
            }
        ){
            Text("Clear Data")
        }

    }
}
