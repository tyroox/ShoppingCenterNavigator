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
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.wineBerry

@Composable
fun About(selectedItem: MutableState<Int>){
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
                    text = "About",
                    style = MaterialTheme.typography.h5,
                    color = wineBerry
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth().verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.login_icon), contentDescription = "")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Welcome to the MallPS app! Developed as an engineering design project by a team of three dedicated individuals from Kadir Has University, our app aims to enhance your shopping experience.\n\n" +
                            "We are Barış Atakan Aktaş, Elif Şanlıtürk, and Afife Koç, the minds behind this innovative application. With our user-friendly interface, the app allows you to effortlessly navigate through shopping centers.\n\n" +
                            "Say goodbye to getting lost in malls or struggling to find stores. Our app provides optimal routes, considering your current location. Thanks to our algorithms, our app offers precise directions and location information. We express gratitude to Kadir Has University and our mentors for their support.\n\n" +
                            "Experience the MallPS app, designed to enhance your shopping journey. Contact us with any questions or feedback. Happy shopping!\n\n" +
                            "Contact Information:\n" +
                            "\nBarış Atakan Aktaş\nbarisatakan.aktas@stu.khas.edu.tr" +
                            "\n\nElif Şanlıtürk\nelifsanliturk@stu.khas.edu.tr" +
                            "\n\nAfife Koç\nafifekoc@stu.khas.edu.tr",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
            }
        }
    }
}