package com.example.shoppingcenternavigator

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.blueHosta
import com.example.shoppingcenternavigator.ui.theme.purplishPink
import com.example.shoppingcenternavigator.ui.theme.wineBerry


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    HomePage(selectedItem)
    Settings(navController, selectedItem, LocalContext.current)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    var selectedMall = SelectedShops.selectedMall
    val alertDialog = remember { mutableStateOf(value = true) }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Surface (modifier = Modifier.fillMaxSize()){
                VerticalGradient()
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)) {
                // Add the Modifier.padding modifier with bottom padding of 56.dp
                // to the Box composable that wraps the content

                if (selectedItem.value == 0){
                    when (selectedMall) {
                        -1 -> {
                            HomePage(selectedItem = selectedItem)
                        }
                        0 -> {
                            MallSearchBar(navController = navController)
                        }
                        1 -> {
                            MallSearchBar(navController = navController)
                        }
                    }
                }
                if (selectedItem.value == 1){
                    GPS()
                }
                if (selectedItem.value == 2){
                    if (selectedMall == -1){
                        if (alertDialog.value){
                            AlertDialog(
                                onDismissRequest = { alertDialog.value = false },
                                text = { Text(text = stringResource(id = R.string.chooseMallErrorMessage),
                                    color = wineBerry, fontSize = 18.sp) },
                                confirmButton = { Text(text = stringResource(id = R.string.confirmButton),
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable {
                                            alertDialog.value = false
                                            selectedItem.value = 0
                                        },
                                    color = wineBerry)},
                                backgroundColor = Color.White
                            )
                        }
                        alertDialog.value = true
                    }
                    else{
                        Stores(selectedItem = selectedItem, navController)
                    }
                }
                if (selectedItem.value == 4){
                    Settings(navController = navController, selectedItem, LocalContext.current)
                }
                if (selectedItem.value == 5){
                    FloorPlans(selectedItem)
                }
                if (selectedItem.value == 6){
                    FAQPage(selectedItem)
                }
                if (selectedItem.value == 7){
                    About(selectedItem)
                }
            }
        },
        bottomBar = {
            // BottomAppBar code
            BottomAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                content = {
                    BottomNavigation(
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent,
                    ) {
                        BottomNavigationItem(
                            selected = selectedItem.value == 0 ,
                            onClick = { selectedItem.value = 0 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.homepage),
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.homePage))},
                            selectedContentColor = wineBerry,
                            unselectedContentColor = Color.White
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 1 ,
                            onClick = { selectedItem.value = 1 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "")
                            },
                            label = { Text(text = "GPS")},
                            selectedContentColor = wineBerry,
                            unselectedContentColor = Color.White

                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 2 ,
                            onClick = { selectedItem.value = 2 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.store),
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.stores))},
                            selectedContentColor = wineBerry,
                            unselectedContentColor = Color.White

                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 4,
                            onClick = { selectedItem.value = 4 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "")
                            },
                            label = { Text(text = stringResource(id = R.string.settings))},
                            selectedContentColor = wineBerry,
                            unselectedContentColor = Color.White
                        )
                    }
                }
            )
        }//,
        //backgroundColor = colorResource(id = R.color.isabelline)
    )
}