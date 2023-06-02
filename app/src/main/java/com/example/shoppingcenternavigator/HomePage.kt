package com.example.shoppingcenternavigator

import SearchBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(selectedItem: MutableState<Int>){
    val keyboardController = LocalSoftwareKeyboardController.current

    var selectedBoxIndex by remember { mutableStateOf(-1) }

    SearchBar(variables = malls, onSearch = { searchQuery ->
        // Handle search query
        // Perform the desired search operation
    }, onBoxClick = { clickedIndex ->
        selectedBoxIndex = clickedIndex
        if(selectedBoxIndex == 0){
            SelectedShops.selectedMall = 0
            selectedItem.value = 0
        }
        else if(selectedBoxIndex == 1){
            SelectedShops.selectedMall = 1
            selectedItem.value = 0
        }
        keyboardController?.hide()
    })
}