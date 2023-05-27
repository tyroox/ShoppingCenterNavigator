package com.example.shoppingcenternavigator

import SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Stores(){
    val keyboardController = LocalSoftwareKeyboardController.current

    val stores = shops.sortedBy { it.Name }

    var selectedBoxIndex by remember { mutableStateOf(-1) }

    StoreSearchBar(variables = stores, onSearch = { searchQuery ->
        // Handle search query
        // Perform the desired search operation
    }, onBoxClick = { clickedIndex ->
        selectedBoxIndex = clickedIndex

        keyboardController?.hide()
    })
}