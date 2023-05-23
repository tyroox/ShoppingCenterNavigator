package com.example.shoppingcenternavigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SelectedShops {
    var selectedOptionFromIndex by mutableStateOf(0)
    var selectedOptionToIndex by mutableStateOf(0)
}