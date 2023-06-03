package com.example.shoppingcenternavigator

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.wineBerry
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
    val emailAndPasswordDoesNotMatchErrorMessage = stringResource(id = R.string.emailAndPasswordDoesNotMatchErrorMessage)
    val enterCredentialsMessage = stringResource(id = R.string.enterCredentialsMessage)


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
                    modifier = Modifier.size(250.dp)
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
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min), // Set the same height as text fields
                    label = { Text(stringResource(id = R.string.password), color = wineBerry) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.white),
                        focusedIndicatorColor = colorResource(id = R.color.white),
                        textColor = colorResource(id = R.color.white),
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility),
                                contentDescription = "Toggle Password Visibility",
                                tint = wineBerry
                            )
                        }
                    }
                )

                Text(text = "Şifreni mi unuttun?", color = Color.White, modifier = Modifier.align(
                    Alignment.End
                ).padding(end = 24.dp).clickable {
                    navController.navigate("ForgotPasswordPage")
                })

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
                                            scaffoldState.snackbarHostState.showSnackbar(message = emailAndPasswordDoesNotMatchErrorMessage)
                                        }
                                        keyboardController?.hide()
                                    }
                                }
                            }
                            else{
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = enterCredentialsMessage)
                                }
                                keyboardController?.hide()
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min), // Set the same height as text fields
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.white))
                    ) {
                        Text(text = stringResource(id = R.string.loginButton), color = wineBerry)
                    }
                }

                Divider(
                    color = colorResource(id = R.color.white),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = (24.dp))
                )

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    Text(stringResource(id = R.string.doNotHaveAccountButton), color = wineBerry)
                    TextButton(onClick = {
                        navController.navigate("RegisterPage")
                        keyboardController?.hide()
                    }) {
                        Text(stringResource(id = R.string.registerButton), color = colorResource(id = R.color.white))
                    }
                }
            }
        }, backgroundColor = colorResource(id = R.color.white)
    )
}