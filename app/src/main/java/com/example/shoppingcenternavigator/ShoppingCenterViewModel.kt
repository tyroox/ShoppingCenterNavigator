package com.example.shoppingcenternavigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class ShoppingCenterViewModel: ViewModel() {
    private val _searchtext = MutableStateFlow("")
    val searchText = _searchtext.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching =  _isSearching.asStateFlow()

    private val _shoppingCenters = MutableStateFlow((allShoppingCenter))
    val shoppingCenters = searchText
        .combine(_shoppingCenters){ text, shoppingCenters ->
            if(text.isBlank()){
                shoppingCenters
            }
            else{
                shoppingCenters.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _shoppingCenters.value
        )

    fun onSearchTextChange(text:String){
        _searchtext.value = text
    }

}

data class ShoppingCenter(
    val name: String
){
    fun doesMatchSearchQuery(query:String):Boolean{

        //can be changed later
        val matchingCombinations = listOf(
            "$name"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true) }
    }
}

val allShoppingCenter = listOf(
    ShoppingCenter("Carousel"),
    ShoppingCenter("Capacity")
)