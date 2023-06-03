package com.example.shoppingcenternavigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MallSearchBar(navController: NavController) {
    var selectedShoppingCenter = ""

    var xShop = carouselShops

    // carousel
    if (SelectedShops.selectedMall == 0){
        selectedShoppingCenter = malls[0]
        xShop = carouselShops
    }
    // capacity
    else if (SelectedShops.selectedMall == 1){
        selectedShoppingCenter = malls[1]
        xShop = capacityShops
    }

    var expanded by remember { mutableStateOf(false) }
    var expandedFrom by remember { mutableStateOf(false) }
    var expandedTo by remember { mutableStateOf(false) }
    var searchTextFrom by remember { mutableStateOf("") }
    var searchTextTo by remember { mutableStateOf("") }
    var selectedOptionFrom by remember { mutableStateOf("") }
    var selectedOptionTo by remember { mutableStateOf(SelectedShops.selectedStoreFromStores) }
    val selectedOptionFromIndex = SelectedShops.selectedOptionFromIndex
    val selectedOptionToIndex = SelectedShops.selectedOptionToIndex
    if (SelectedShops.selectedStoreFromStores != ""){
        SelectedShops.selectedOptionToIndex = xShop.indexOfFirst { it.Name == SelectedShops.selectedStoreFromStores }
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val sameStoreErrorMessage = stringResource(id = R.string.sameStoreErrorMessage)

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) {
                Snackbar(
                    backgroundColor = Color.White,
                    contentColor = wineBerry,
                    snackbarData = it
                )
            }
        },
        content = {
            Surface (modifier = Modifier.fillMaxSize()){
                VerticalGradient()
            }
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (fromTextField, toTextField, button) = createRefs()

                Box{
                    Text(text = selectedShoppingCenter,
                        Modifier
                            .padding(16.dp)
                            .clickable { expanded = true }, color = wineBerry, fontWeight = FontWeight.Bold)

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                SelectedShops.selectedMall = 0
                                expanded = false

                            },
                            content = { Text(text = "Carousel", color = wineBerry) }
                        )
                        DropdownMenuItem(
                            onClick = {
                                SelectedShops.selectedMall = 1
                                expanded = false
                            },
                            content = { Text(text = "Capacity", color = wineBerry) }
                        )
                    }
                }

                TextField(
                    modifier = Modifier
                        .clickable {
                            expandedFrom = !expandedFrom
                        }
                        .constrainAs(fromTextField) {
                            top.linkTo(parent.top, margin = 64.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = selectedOptionFrom,
                    onValueChange = { /* Nothing to do here */ },
                    readOnly = true,
                    enabled = false,
                    label = { Text(
                        stringResource(id = R.string.fromStore),
                        color = wineBerry, fontWeight = FontWeight.Bold) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        disabledTextColor = wineBerry,
                        cursorColor = wineBerry,
                        unfocusedIndicatorColor = wineBerry,
                        focusedIndicatorColor = wineBerry
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { expandedFrom = !expandedFrom }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                tint = wineBerry
                            )
                        }
                    }
                )

                TextField(
                    modifier = Modifier
                        .clickable {
                            expandedTo = !expandedTo
                        }
                        .constrainAs(toTextField) {
                            top.linkTo(fromTextField.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = selectedOptionTo,
                    onValueChange = { /* Nothing to do here */ },
                    readOnly = true,
                    enabled = false,
                    label = { Text(
                        stringResource(id = R.string.toStore),
                        color = wineBerry, fontWeight = FontWeight.Bold) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        disabledTextColor = wineBerry,
                        cursorColor = wineBerry,
                        unfocusedIndicatorColor = wineBerry,
                        focusedIndicatorColor = wineBerry
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { expandedTo = !expandedTo }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                tint = wineBerry
                            )
                        }
                    }
                )

                Button(
                    modifier = Modifier
                        .constrainAs(button) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 16.dp, horizontal = 64.dp),
                    onClick = {
                        if (selectedOptionFromIndex == selectedOptionToIndex) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = sameStoreErrorMessage)
                                keyboardController?.hide()
                            }
                        } else {
                            navController.navigate("WayFindingAlgorithm")
                            keyboardController?.hide()
                        }
                    },
                    enabled = selectedOptionFrom.isNotEmpty() && selectedOptionTo.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(text = stringResource(id = R.string.drawPathButton),
                        color = wineBerry)
                }
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
                                .background(colorResource(id = R.color.isabelline))
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = searchTextFrom,
                                onValueChange = { searchTextFrom = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(stringResource(id = R.string.searchStoreButton), color = wineBerry) },
                                keyboardActions = KeyboardActions(
                                    onDone = {keyboardController?.hide()}),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = colorResource(id = R.color.isabelline),
                                    cursorColor = wineBerry,
                                    unfocusedIndicatorColor = wineBerry,
                                    focusedIndicatorColor = wineBerry
                                ),
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                val filteredOptionsFrom = xShop.filter { it.Name.contains(searchTextFrom, ignoreCase = true) }
                                    .sortedBy { it.Name } // Sort the list alphabetically

                                items(filteredOptionsFrom) { option ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOptionFrom = option.Name
                                            SelectedShops.selectedOptionFromIndex = xShop.indexOfFirst { it.Name == selectedOptionFrom }
                                            Log.d("from", "$selectedOptionFromIndex")
                                            expandedFrom = false
                                            searchTextFrom = ""
                                            keyboardController?.hide()
                                        }
                                    ) {
                                        Text(option.Name)
                                    }
                                }
                            }
                            Divider(modifier = Modifier.fillMaxWidth(), color = wineBerry)

                            Button(
                                onClick = { expandedFrom = false },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(top = 16.dp, bottom = 8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.white))
                            ) {
                                Text(stringResource(id = R.string.closeButton), color = wineBerry)
                            }
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
                                .background(color = colorResource(id = R.color.isabelline))
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = searchTextTo,
                                onValueChange = { searchTextTo = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(stringResource(id = R.string.searchStoreButton), color = wineBerry) },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = colorResource(id = R.color.isabelline),
                                    cursorColor = wineBerry,
                                    unfocusedIndicatorColor = wineBerry,
                                    focusedIndicatorColor = wineBerry
                                ),
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                val filteredOptionsTo = xShop.filter { it.Name.contains(searchTextTo, ignoreCase = true) }
                                    .sortedBy { it.Name }

                                items(filteredOptionsTo) { option ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOptionTo = option.Name
                                            SelectedShops.selectedStoreFromStores = ""
                                            SelectedShops.selectedOptionToIndex = xShop.indexOfFirst { it.Name == selectedOptionTo }
                                            Log.d("to", "$selectedOptionToIndex")
                                            expandedTo = false
                                            searchTextTo = ""
                                            keyboardController?.hide()
                                        }
                                    ) {
                                        Text(option.Name)
                                    }
                                }
                            }
                            Divider(modifier = Modifier.fillMaxWidth(), color = wineBerry)

                            Button(
                                onClick = { expandedTo = false
                                    keyboardController?.hide()
                                },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(top = 16.dp, bottom = 8.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.white)
                                )
                            ) {
                                Text(stringResource(id = R.string.closeButton), color = wineBerry)

                            }
                        }
                    }
                }
            }
    )
}

