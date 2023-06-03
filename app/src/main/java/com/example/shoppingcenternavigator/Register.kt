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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.purplishPink
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scaffoldState = rememberScaffoldState()
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val password1 = remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by rememberSaveable { mutableStateOf(value = false) }
    var passwordVisibility1 by rememberSaveable { mutableStateOf(value = false) }
    var emailState by remember { mutableStateOf(false) }
    var passwordState by remember { mutableStateOf(false) }
    var passwordState1 by remember { mutableStateOf(false) }
    val emailValidationRegex =
        Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", RegexOption.IGNORE_CASE)
    val scope = rememberCoroutineScope()
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val alertDialog = remember { mutableStateOf(value = true) }
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
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
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
                    painter = painterResource(id = R.drawable.register_icon),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
                Column() {
                    TextField(
                        label = {
                            Text("E-Posta", color = wineBerry)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        value = email.value,
                        singleLine = true,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        onValueChange = {
                            email.value = it
                            emailState = emailValidationRegex.matches(it.text)
                        },

                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.white),
                            focusedIndicatorColor = colorResource(id = R.color.white),
                            textColor = colorResource(id = R.color.white)
                        )
                    )
                    // Warning for invalid email
                    if (email.value.text.isNotEmpty() && !emailState) {
                        Text(
                            text = "Geçerli bir e-posta adresi giriniz.",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                }

                Column() {
                    TextField(
                        label = {
                            Text("Şifre", color = wineBerry)
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility),
                                    contentDescription = "",
                                    tint = wineBerry
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        value = password.value,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        onValueChange = {
                            password.value = it
                            passwordState = password.value.text.length >= 6
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.white),
                            focusedIndicatorColor = colorResource(id = R.color.white),
                            textColor = colorResource(id = R.color.white)
                        )
                    )

                    // Warning for short password
                    if (password.value.text.isNotEmpty() && password.value.text.length < 6) {
                        Text(
                            text = "Şifre en az 6 karakter olmalıdır.",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                }

                Column() {
                    TextField(
                        label = {
                            Text("Tekrar şifre giriniz", color = wineBerry)
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility1) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility1 = !passwordVisibility1
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility),
                                    contentDescription = "",
                                    tint = wineBerry
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        value = password1.value,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        onValueChange = {
                            password1.value = it
                            passwordState1 = password.value.text.length >= 6
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = colorResource(id = R.color.white),
                            focusedIndicatorColor = colorResource(id = R.color.white),
                            textColor = colorResource(id = R.color.white)
                        )
                    )

                    // Warning for not matching passwords
                    if (password.value.text != password1.value.text) {
                        Text(
                            text = "Şifreler eşleşmiyor!",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (email.value.text.trim().isEmpty() && password.value.text.trim()
                                    .isEmpty()
                            ) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Lütfen boş alanları doldurunuz")
                                }
                                keyboardController?.hide()
                            } else if (password.value.text.trim().isNotEmpty()) {
                                if (password.value.text.trim() != password1.value.text.trim()) {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "Şifreler eşleşmiyor.")
                                    }
                                    keyboardController?.hide()
                                } else if (password.value.text.trim() == password1.value.text.trim()) {
                                    auth.createUserWithEmailAndPassword(
                                        email.value.text.trim(),
                                        password.value.text.trim()
                                    ).addOnCompleteListener(context) { task ->
                                        if (task.isSuccessful) {
                                            navController.navigate("MainPage")
                                            keyboardController?.hide()
                                        } else {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(message = "Bu e-posta adresi zaten kayıtlı.")
                                            }
                                            keyboardController?.hide()
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min), // Set the same height as text fields
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.white)
                        ), enabled = emailState && passwordState && passwordState1

                    ) {
                        Text(text = "Kayıt Ol", color = wineBerry)
                    }
                }

                Divider(
                    color = colorResource(id = R.color.white),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = (24.dp))
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text("Hesabınız var mı?", color = wineBerry)
                    TextButton(onClick = {
                        navController.navigate("LoginPage")
                        keyboardController?.hide()
                    }) {
                        Text("Giriş yapın.", color = colorResource(id = R.color.white))
                    }
                }
            }
        },
        backgroundColor = colorResource(id = R.color.white)
    )
}