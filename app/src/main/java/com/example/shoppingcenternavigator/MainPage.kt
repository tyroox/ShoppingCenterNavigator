package com.example.shoppingcenternavigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(navController: NavController) {
    val selectedItem = remember { mutableStateOf(2) }
    HomePage(navController, selectedItem)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    var selectedMall = SelectedShops.selectedMall

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.padding(bottom = 56.dp)) {
                // Add the Modifier.padding modifier with bottom padding of 56.dp
                // to the Box composable that wraps the content
                if (selectedItem.value == 0){
                    GPS()
                }
                if (selectedItem.value == 1){
                    FloorPlans()
                }
                if (selectedItem.value == 2){
                    if (selectedMall == 0){
                        HomePage(navController = navController, selectedItem = selectedItem)
                    }
                    else{
                        ShopSearchBar(navController = navController)
                    }
                }
                if (selectedItem.value == 3){
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
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "")
                            },
                            label = { Text(text = "GPS")},
                            selectedContentColor = colorResource(id = R.color.orangePeel),
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 1 ,
                            onClick = { selectedItem.value = 1 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "")
                            },
                            label = { Text(text = "Kat Planları")},
                            selectedContentColor = colorResource(id = R.color.orangePeel),
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 2 ,
                            onClick = { selectedItem.value = 2 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "")
                            },
                            label = { Text(text = "Ana Sayfa")},
                            selectedContentColor = colorResource(id = R.color.orangePeel),
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 3 ,
                            onClick = { selectedItem.value = 3 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "")
                            },
                            label = { Text(text = "Ayarlar")},
                            selectedContentColor = colorResource(id = R.color.orangePeel),
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                    }
                }
            )
        },
        backgroundColor = colorResource(id = R.color.isabelline)
    )
}