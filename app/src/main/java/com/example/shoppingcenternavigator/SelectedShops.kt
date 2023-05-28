package com.example.shoppingcenternavigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SelectedShops {
    var selectedOptionFromIndex by mutableStateOf(-1)
    var selectedOptionToIndex by mutableStateOf(-1)
    var selectedMall by mutableStateOf(0)
    var selectedStoreFromStores by mutableStateOf("")
}

