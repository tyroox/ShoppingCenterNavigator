package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.wineBerry

/*
Information about the application is displayed in this composable function called About.
To structure and display the UI elements, the code provides use of a number of Jetpack
Compose composables, including Box, Column, Row, Icon, Text, Image, and Spacer.
 */
@Composable
fun About(selectedItem: MutableState<Int>){
    /*
    BackHandler is a composable that handles the back button press on Android devices
    It updates the selectedItem value to 4 when the back button is pressed so that we
    can return to settings page
     */
    BackHandler(onBack = {selectedItem.value = 4})
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 2.dp, end = 4.dp)
                        .clickable { selectedItem.value = 4 },
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.aboutButton),
                    style = MaterialTheme.typography.h5,
                    color = wineBerry
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.login_icon), contentDescription = "")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.aboutText),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
            }
        }
    }
}