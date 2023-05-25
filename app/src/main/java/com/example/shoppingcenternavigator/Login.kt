package com.example.shoppingcenternavigator

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scaffoldState = rememberScaffoldState()
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by rememberSaveable { mutableStateOf(value = false) }
    val scope = rememberCoroutineScope()
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(100))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) {
                Snackbar(
                    backgroundColor = colorResource(id = R.color.orangePeel),
                    contentColor = colorResource(id = R.color.isabelline),
                    snackbarData = it
                )
            }
        },
        content =
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_center_navigator_app_icon),
                    contentDescription = null,
                    modifier = Modifier.size(260.dp)
                )
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min), // Set the same height as text fields
                    label = { Text("E-Posta", color = colorResource(id = R.color.caribbeanCurrent)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent)
                    )
                )
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min), // Set the same height as text fields
                    label = { Text("Şifre", color = colorResource(id = R.color.caribbeanCurrent)) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent)
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility),
                                contentDescription = "Toggle Password Visibility",
                                tint = colorResource(id = R.color.caribbeanCurrent)
                            )
                        }
                    }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (email.value.text.trim().isNotEmpty() and password.value.text.trim().isNotEmpty()){
                                auth.signInWithEmailAndPassword(
                                    email.value.text.trim(),
                                    password.value.text.trim()
                                ).addOnCompleteListener(context){ task ->
                                    if (task.isSuccessful){
                                        navController.navigate("MainPage")
                                        keyboardController?.hide()
                                    }else{
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(message = "E-posta ile şifre uyuşmuyor veya doğru değil.")
                                        }
                                        keyboardController?.hide()
                                    }
                                }
                            }
                            else{
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Lütfen e-posta ve şifre giriniz.")
                                }
                                keyboardController?.hide()
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min), // Set the same height as text fields
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orangePeel))
                    ) {
                        Text(text = "Giriş Yap", color = colorResource(id = R.color.isabelline))
                    }
                }

                Divider(
                    color = colorResource(id = R.color.orangePeel),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = (24.dp))
                )
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    Text("Hesabınız yok mu?", color = colorResource(id = R.color.orangePeel))
                    TextButton(onClick = {
                        navController.navigate("RegisterPage")
                        keyboardController?.hide()
                    }) {
                        Text("Kayıt olun.", color = colorResource(id = R.color.caribbeanCurrent))
                    }
                }
            }
        }, backgroundColor = colorResource(id = R.color.isabelline)
    )
}
