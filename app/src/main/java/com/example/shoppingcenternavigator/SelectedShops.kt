package com.example.shoppingcenternavigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/*
This is an object class to keep our data to use and change it in different files.
 */
object SelectedShops {
    var selectedOptionFromIndex by mutableStateOf(-1)
    var selectedOptionToIndex by mutableStateOf(-1)
    var selectedMall by mutableStateOf(-1)
    var selectedStoreFromStores by mutableStateOf("")
}

