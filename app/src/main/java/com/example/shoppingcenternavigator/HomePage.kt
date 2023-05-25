package com.example.shoppingcenternavigator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomePage(navController: NavController, selectedItem: MutableState<Int>){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel = viewModel<ShoppingCenterViewModel>()
        val searchText by viewModel.searchText.collectAsState()
        val shoppingCenters by viewModel.shoppingCenters.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "AVM bul", color = colorResource(id = R.color.caribbeanCurrent))},
                trailingIcon = {
                    Icon(painter = painterResource(id = R.drawable.search), contentDescription = "",
                        tint = colorResource(id = R.color.caribbeanCurrent))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.isabelline),
                    cursorColor = colorResource(id = R.color.caribbeanCurrent),
                    unfocusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent),
                    focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent)
                ),
            )
            Spacer(modifier = Modifier.height((16.dp)))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                items(shoppingCenters){shoppingCenter ->
                    Text(text = "${shoppingCenter.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .clickable {
                                selectedItem.value = 5
                            })
                }
            }
        }
    }
}