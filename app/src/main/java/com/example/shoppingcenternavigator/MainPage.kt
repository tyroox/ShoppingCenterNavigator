package com.example.shoppingcenternavigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    HomePage(selectedItem)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    var selectedMall = SelectedShops.selectedMall
    val alertDialog = remember { mutableStateOf(value = true) }


    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.padding(bottom = 56.dp)) {
                // Add the Modifier.padding modifier with bottom padding of 56.dp
                // to the Box composable that wraps the content
                if (selectedItem.value == 0){
                    if (selectedMall == 0){
                        HomePage(selectedItem = selectedItem)
                    }
                    else{
                        ShopNavigatorSearchBar(navController = navController)
                    }
                }
                if (selectedItem.value == 1){
                    GPS()
                }
                if (selectedItem.value == 2){
                    if (selectedMall == 0){
                        if (alertDialog.value){
                            AlertDialog(
                                onDismissRequest = { alertDialog.value = false },
                                text = { Text(text = stringResource(id = R.string.chooseMallErrorMessage),
                                    color = colorResource(id = R.color.isabelline), fontSize = 18.sp) },
                                confirmButton = { Text(text = stringResource(id = R.string.confirmButton),
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            alertDialog.value = false
                                            selectedItem.value = 0
                                        },
                                    color = colorResource(id = R.color.isabelline))},
                                backgroundColor = colorResource(id = R.color.caribbeanCurrent)
                            )
                        }
                        alertDialog.value = true
                    }
                    else{
                        Stores(selectedItem = selectedItem)
                    }
                }
                if (selectedItem.value == 3){
                    if (selectedMall == 0){
                        if (alertDialog.value){
                            AlertDialog(
                                onDismissRequest = { alertDialog.value = false },
                                text = { Text(text = stringResource(id = R.string.chooseMallErrorMessage),
                                    color = colorResource(id = R.color.isabelline), fontSize = 18.sp) },
                                confirmButton = { Text(text = stringResource(id = R.string.confirmButton),
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            alertDialog.value = false
                                            selectedItem.value = 0
                                        },
                                    color = colorResource(id = R.color.isabelline))},
                                backgroundColor = colorResource(id = R.color.caribbeanCurrent)
                            )
                        }
                        alertDialog.value = true
                    }
                    else{
                        FloorPlans()
                    }
                }
                if (selectedItem.value == 4){
                    Settings()
                }
            }
        },
        bottomBar = {
            // BottomAppBar code
            BottomAppBar(
                backgroundColor = colorResource(id = R.color.moonstone),
                content = {
                    BottomNavigation(backgroundColor = colorResource(id = R.color.moonstone)) {
                        BottomNavigationItem(
                            selected = selectedItem.value == 0 ,
                            onClick = { selectedItem.value = 0 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.homepage),
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.homePage), fontSize = 10.sp)},
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 1 ,
                            onClick = { selectedItem.value = 1 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "")
                            },
                            label = { Text(text = "GPS", fontSize = 10.sp)},
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = colorResource(id = R.color.isabelline)

                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 2 ,
                            onClick = { selectedItem.value = 2 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.store),
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.stores), fontSize = 10.sp)},
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = colorResource(id = R.color.isabelline)

                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 3 ,
                            onClick = { selectedItem.value = 3 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.floorplan),
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.floorPlans), fontSize = 10.sp)},
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 4,
                            onClick = { selectedItem.value = 4 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.settings), fontSize = 10.sp)},
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = colorResource(id = R.color.isabelline))

                    }
                }
            )
        },
        backgroundColor = colorResource(id = R.color.isabelline)
    )
}