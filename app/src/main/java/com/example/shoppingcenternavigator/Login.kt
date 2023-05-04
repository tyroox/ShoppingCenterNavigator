package com.example.shoppingcenternavigator

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun Login(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {SnackbarHost(it){
            Snackbar(
                backgroundColor = colorResource(id = R.color.orangePeel),
                contentColor = colorResource(id = R.color.isabelline),
                snackbarData = it)
        }
        },
        topBar = {
            TopAppBar(title = {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Image(painter = painterResource(id = R.drawable.shopping_center_navigator_app_icon),
                        contentDescription = "",
                        Modifier.size(150.dp),
                    )
                }
            },
                backgroundColor = colorResource(id = R.color.moonstone))
        },
        content =

            {val email = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }
            var passwordVisibility by rememberSaveable { mutableStateOf(value = false) }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(75.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    label = {
                        Text("E-Posta", color = colorResource(id = R.color.caribbeanCurrent))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    value = email.value,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        email.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )

                TextField(
                    label = {
                        Text("Şifre", color = colorResource(id = R.color.caribbeanCurrent))
                    },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    value = password.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        password.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )
                Button(onClick = {
                    if (email.value.text.trim().isNotEmpty() and password.value.text.trim().isNotEmpty()){
                        auth.signInWithEmailAndPassword(
                            email.value.text.trim(),
                            password.value.text.trim()
                        ).addOnCompleteListener(context){ task ->
                            if (task.isSuccessful){
                                navController.navigate("MainPage")
                            }else{
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "E-posta ile şifre uyuşmuyor veya doğru değil.")
                                }
                            }
                        }
                    }
                    else{
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = "Lütfen e-posta ve şifre giriniz.")
                        }
                    }

                },
                    Modifier.size(200.dp,50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.orangePeel))
                ) {
                    Text(text = "Giriş Yap", color = Color.White)
                }
            }

        },
        backgroundColor = colorResource(id = R.color.isabelline),
    )
}
