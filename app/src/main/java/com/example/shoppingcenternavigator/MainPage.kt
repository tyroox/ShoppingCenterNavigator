package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun MainPage(navController: NavController) {
    val selectedItem = remember { mutableStateOf(3) }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "",
                            tint = colorResource(id = R.color.caribbeanCurrent),
                            modifier = Modifier.clickable {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            })
                        Image(painter = painterResource(id = R.drawable.shopping_center_navigator_app_icon),
                            contentDescription = "",
                            Modifier.size(50.dp),
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.moonstone)
            )
        },

        content = {            
            if (selectedItem.value == 0){
                Stores()
            }
            if (selectedItem.value == 1){
                Settings()
            }
            if (selectedItem.value == 2){
                GPS()
            }
            if (selectedItem.value == 3){
                HomePage()
            }
            if (selectedItem.value == 4){
                User(navController = navController)
            }
        },
        drawerContent = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
                .background(colorResource(id = R.color.moonstone))) {
                Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "",
                        tint = colorResource(id = R.color.caribbeanCurrent),
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }

            DropdownMenuItem(onClick = {
                selectedItem.value = 0
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }) {
                Text(text = "Mağazalar")
            }
            DropdownMenuItem(onClick = {
                selectedItem.value = 1
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }) {
                Text(text = "Ayarlar")
            }

            BackHandler(onBack = {
                if(scaffoldState.drawerState.isOpen){
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            })
        },
        drawerBackgroundColor = colorResource(id = R.color.isabelline),
        bottomBar = {
            BottomAppBar(
                backgroundColor = colorResource(id = R.color.moonstone),
                content = {
                    BottomNavigation(backgroundColor = colorResource(id = R.color.moonstone)) {
                        BottomNavigationItem(
                            selected = selectedItem.value == 2 ,
                            onClick = { selectedItem.value = 2 },
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
                            selected = selectedItem.value == 3 ,
                            onClick = { selectedItem.value = 3 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.homepage),
                                    contentDescription = "")
                            },
                            label = { Text(text = "Ana Sayfa")},
                            selectedContentColor = colorResource(id = R.color.orangePeel),
                            unselectedContentColor = colorResource(id = R.color.isabelline)
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == 4 ,
                            onClick = { selectedItem.value = 4 },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.user),
                                    contentDescription = "")
                            },
                            label = { Text(text = "Kullanıcı")},
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
