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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.purplishPink
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassword(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scaffoldState = rememberScaffoldState()
    val email = remember { mutableStateOf(TextFieldValue()) }
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
                    backgroundColor = Color.White,
                    contentColor = wineBerry,
                    snackbarData = it
                )
            }
        },
        content =
        {
            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background) {
                VerticalGradient()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_icon),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp),
                )
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min), // Set the same height as text fields
                    label = { Text(stringResource(id = R.string.email), color = wineBerry) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.white),
                        focusedIndicatorColor = colorResource(id = R.color.white),
                        textColor = colorResource(id = R.color.white)
                    )
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (email.value.text.trim().isNotEmpty()){
                                auth.sendPasswordResetEmail(email.value.text.trim())
                                    .addOnCompleteListener(context){ task ->
                                    if (task.isSuccessful){
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Şifre sıfırlama linki e-postanıza gönderildi. Sıfırladıktan sonra giriş yapabilirsiniz.")
                                        }
                                        keyboardController?.hide()
                                    }else{
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(message = "E-posta sisteme kayıtlı değil veya yanlış girildi.")
                                        }
                                        keyboardController?.hide()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min), // Set the same height as text fields
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.white))
                    ) {
                        Text(text = "Şifre sıfırlama e-postası gönder", color = wineBerry)
                    }
                }

                Divider(
                    color = colorResource(id = R.color.white),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = (24.dp))
                )

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    TextButton(onClick = {
                        navController.navigate("LoginPage")
                        keyboardController?.hide()
                    }) {
                        Text("Giriş yap", color = colorResource(id = R.color.white))
                    }
                }
            }
        }, backgroundColor = colorResource(id = R.color.white)
    )
}