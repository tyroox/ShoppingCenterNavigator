package com.example.shoppingcenternavigator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.wineBerry

/*
This is our stores function which is built to display our stores page in boxes of two in a row, with
search bar functionality to search for stores. Also within this page you can reach the floor plans.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Stores(selectedItem: MutableState<Int>, navController: NavController){
    val keyboardController = LocalSoftwareKeyboardController.current

    var xShop = carouselShops

    if (SelectedShops.selectedMall == 0){
        xShop = carouselShops
    }
    else if (SelectedShops.selectedMall == 1){
        xShop = capacityShops
    }

    val stores = xShop.sortedBy { it.Name }

    val alertDialog = remember { mutableStateOf(value = false) }


    if (alertDialog.value){
        AlertDialog(
            onDismissRequest = { alertDialog.value = false
                SelectedShops.selectedStoreFromStores = ""
            },
            text = { Text(text = stringResource(id = R.string.getRouteToStoreText),
                color = wineBerry, fontSize = 18.sp) },
            confirmButton = {
                Text(text = stringResource(id = R.string.noText),
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            SelectedShops.selectedStoreFromStores = ""
                            alertDialog.value = false
                        },
                    color = wineBerry
                )
            },
            dismissButton = {
                Text(text = stringResource(id = R.string.yesText),
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            selectedItem.value = 0
                        },
                    color = wineBerry
                )

            },
            backgroundColor = Color.White
        )
    }


    StoreSearchBar(variables = stores, onSearch = { searchQuery ->
        // Handle search query
        // Perform the desired search operation
        // Transfer the name of the store that user wants to get directions to the home page
    }, onBoxClick = { clickedName ->
        alertDialog.value = true

        val shopFromBoxNameIndex= xShop.indexOfFirst { it.Name == clickedName }
        SelectedShops.selectedStoreFromStores = xShop[shopFromBoxNameIndex].Name

        keyboardController?.hide()

    }, selectedItem)
}