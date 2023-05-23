package com.example.shoppingcenternavigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopSearchBar(navController: NavController) {

    var expandedFrom by remember { mutableStateOf(false) }
    var expandedTo by remember { mutableStateOf(false) }
    var searchTextFrom by remember { mutableStateOf("") }
    var searchTextTo by remember { mutableStateOf("") }
    var selectedOptionFrom by remember { mutableStateOf("") }
    var selectedOptionTo by remember { mutableStateOf("") }
    var selectedOptionFromIndex = SelectedShops.selectedOptionFromIndex
    var selectedOptionToIndex = SelectedShops.selectedOptionToIndex
    val options = shops

    Box(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(
                value = selectedOptionFrom,
                onValueChange = { /* Nothing to do here */ },
                readOnly = true,
                enabled = false,
                label = { Text("Neredesiniz?") },
                trailingIcon = {
                    IconButton(
                        onClick = { expandedFrom = !expandedFrom }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = ""
                        )
                    }
                }
            )
            OutlinedTextField(
                value = selectedOptionTo,
                onValueChange = { /* Nothing to do here */ },
                readOnly = true,
                enabled = false,
                label = { Text("Nereye gitmek istiyorsunuz?") },
                trailingIcon = {
                    IconButton(
                        onClick = { expandedTo = !expandedTo }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
        if (expandedFrom) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { expandedFrom = false },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchTextFrom,
                        onValueChange = { searchTextFrom = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Ara") }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        val filteredOptionsFrom = options.filter { it.Name.contains(searchTextFrom, ignoreCase = true) }
                        items(filteredOptionsFrom) { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionFrom = option.Name
                                    SelectedShops.selectedOptionFromIndex = shops.indexOfFirst { it.Name == selectedOptionFrom }
                                    Log.d("from", "$selectedOptionFromIndex")
                                    expandedFrom = false
                                    searchTextFrom = ""
                                }
                            ) {
                                Text(option.Name)
                            }
                        }
                    }

                    Divider(modifier = Modifier.fillMaxWidth())
                    /*
                    Button(
                        onClick = { expandedFrom = false }
                    ) {
                        Text("Kapat")
                    }

                     */
                }
            }
        }
        if (expandedTo) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { expandedTo = false },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchTextTo,
                        onValueChange = { searchTextTo = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Ara") }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        val filteredOptionsTo = options.filter { it.Name.contains(searchTextTo, ignoreCase = true) }
                        items(filteredOptionsTo) { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionTo = option.Name
                                    SelectedShops.selectedOptionToIndex = shops.indexOfFirst { it.Name == selectedOptionTo }
                                    Log.d("to", "$selectedOptionToIndex")
                                    expandedTo = false
                                    searchTextTo = ""
                                }
                            ) {
                                Text(option.Name)
                            }
                        }
                    }

                    Divider(modifier = Modifier.fillMaxWidth())
                    /*
                    Button(
                        onClick = { expandedTo = false }
                    ) {
                        Text("Kapat")
                    }

                     */
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = {
                if (selectedOptionFrom == selectedOptionTo){

                }
                else{

                    navController.navigate("SmoothLineGraph")
                }
            }, enabled = selectedOptionFrom.isNotEmpty() and selectedOptionTo.isNotEmpty()) {
                Text(text = "Yol tarifinizi çiz!")
            }

        }
    }
}
