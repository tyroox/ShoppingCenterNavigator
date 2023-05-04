package com.example.shoppingcenternavigator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun User(navController: NavController) {
    val auth = Firebase.auth
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val alertDialog = remember { mutableStateOf(value = false) }

        if (alertDialog.value){
            AlertDialog(
                onDismissRequest = { alertDialog.value = false },
                text = { Text(text = "Çıkış yapmak istediğinize emin misiniz?",
                    color = colorResource(id = R.color.isabelline), fontSize = 18.sp) },
                confirmButton = {
                    Text(text = "Hayır",
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                alertDialog.value = false
                            },
                        color = colorResource(id = R.color.isabelline))},
                dismissButton = {
                    Text(text = "Evet",
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                auth.signOut()
                                navController.navigate("StartPage")
                            },
                        color = colorResource(id = R.color.isabelline))

                },
                backgroundColor = colorResource(id = R.color.caribbeanCurrent)
            )
        }
        Button(onClick = {
            alertDialog.value = true
        },
            Modifier.size(200.dp,50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.orangePeel)
            )) {
            Text(text = "Çıkış Yap", color = Color.White)
        }
        Text(text = "${auth.currentUser?.email}")
    }
}