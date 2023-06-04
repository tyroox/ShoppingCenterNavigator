package com.example.shoppingcenternavigator

import SearchBar
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

/*
This is the homepage which exits for choosing the mall. After user chooses the mall they can't reach
here again unless they restart the app. The home page becomes mall search bar after selecting the
mall from here inside the main page.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(selectedItem: MutableState<Int>){

    val keyboardController = LocalSoftwareKeyboardController.current

    var selectedBoxName by remember { mutableStateOf("") }

    SearchBar(variables = malls, onSearch = {
        // Handle search query
        // Perform the desired search operation
    }, onBoxClick = { clickedName ->
        selectedBoxName = clickedName
        if(selectedBoxName == "Carousel"){
            SelectedShops.selectedMall = 0
            selectedItem.value = 0
        }
        else if(selectedBoxName == "Capacity"){
            SelectedShops.selectedMall = 1
            selectedItem.value = 0
        }
        keyboardController?.hide()
    })
}