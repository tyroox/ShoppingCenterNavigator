package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun About(selectedItem: MutableState<Int>){
    BackHandler(onBack = {selectedItem.value = 4})
    
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .size(40.dp)
                    .clickable {
                        selectedItem.value = 4
                    }, tint = Color.White
            )
            Text(
                text = "About",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 2.dp),
                color = Color.White
            )
        }
    }
}