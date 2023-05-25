package com.example.shoppingcenternavigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloorPlans() {
    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        val selectedItem = remember { mutableStateOf(0) }

        when (selectedItem.value) {
            -2 -> {
                Image(painter = painterResource(id = R.drawable.carousel_b2),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            -1 -> {
                Image(painter = painterResource(id = R.drawable.carousel_b1),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            0 -> {
                Image(painter = painterResource(id = R.drawable.carousel_0),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            1 -> {
                Image(painter = painterResource(id = R.drawable.carousel_1),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
            2 -> {
                Image(painter = painterResource(id = R.drawable.carousel_2),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize())
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { selectedItem.value = -2 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(text = "-2")
            }
            OutlinedButton(onClick = { selectedItem.value = -1 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(text = "-1")
            }
            OutlinedButton(onClick = { selectedItem.value = 0 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(text = "0")
            }
            OutlinedButton(onClick = { selectedItem.value = 1 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(text = "1")
            }
            OutlinedButton(onClick = { selectedItem.value = 2 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                Text(text = "2")
            }
        }
    }
}

@Preview
@Composable
fun FloorPlansPreview() {
    FloorPlans()
}
