package com.example.shoppingcenternavigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun StartPage(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.shopping_center_navigator_app_icon),
            contentDescription = "", Modifier.size(300.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { navController.navigate("LoginPage")
            },
                Modifier.size(200.dp,50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.orangePeel))) {
                Text(text = "Giriş Yap", color = colorResource(id = R.color.isabelline))
            }
            Button(onClick = { navController.navigate("RegisterPage") },
                Modifier.size(200.dp,50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.orangePeel))) {
                Text(text = "Kayıt Ol", color = colorResource(id = R.color.isabelline))
            }
        }
    }
}
