package com.example.shoppingcenternavigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.MutableState

@Composable
fun User(navController: NavController,selectedItem: MutableState<Int>) {
    BackHandler(onBack = {selectedItem.value = 4})

    val auth = Firebase.auth
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically){
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 0.dp, end = 4.dp)
                    .size(40.dp)
                    .clickable {
                        selectedItem.value = 4
                    }, tint = Color.White)
            Text(
                text = "Account Settings",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 2.dp),
                color = Color.White
            )
        }
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
                                navController.navigate("LoginPage")
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

        Button(onClick = { auth.currentUser?.email?.let { auth.sendPasswordResetEmail(it) } }) {
            Text("Send Password Reset Email")
        }
    }
}