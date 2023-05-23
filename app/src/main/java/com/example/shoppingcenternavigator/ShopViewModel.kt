package com.example.shoppingcenternavigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class ShopViewModel: ViewModel() {
    private val _searchtext = MutableStateFlow("")
    val searchText = _searchtext.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching =  _isSearching.asStateFlow()

    private val _shops = MutableStateFlow((shops))
    val shop1 = searchText
        .combine(_shops){ text, shop ->
            if(text.isBlank()){
                shop
            }
            else{
                shop.filter {
                    it.Name.contains(text, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _shops.value
        )

    fun onSearchTextChange(text:String){
        _searchtext.value = text
    }
}