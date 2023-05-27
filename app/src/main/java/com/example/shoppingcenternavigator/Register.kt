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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scaffoldState = rememberScaffoldState()
    val email  = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val password1 = remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by rememberSaveable { mutableStateOf(value = false) }
    var passwordVisibility1 by rememberSaveable { mutableStateOf(value = false) }
    var emailState by remember { mutableStateOf(false) }
    var passwordState by remember { mutableStateOf(false) }
    var passwordState1 by remember { mutableStateOf(false) }
    val emailValidationRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.com)(.{1,})"
    val scope = rememberCoroutineScope()
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val alertDialog = remember { mutableStateOf(value = true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val fillInTheBlanksErrorMessage = stringResource(id = R.string.fillInTheBlanksErrorMessage)
    val passwordsDoNotMatchErrorMessage = stringResource(id = R.string.passwordsDoNotMatchErrorMessage)
    val emailAlreadyRegisteredErrorMessage = stringResource(id = R.string.emailAlreadyRegisteredErrorMessage)

    if (alertDialog.value){
        AlertDialog(
            onDismissRequest = { alertDialog.value = false },
            text = { Text(text = stringResource(id = R.string.enterValidCredentialsErrorMessage),
                color = colorResource(id = R.color.isabelline), fontSize = 18.sp) },
            confirmButton = { Text(text = stringResource(id = R.string.confirmButton),
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { alertDialog.value = false },
                color = colorResource(id = R.color.isabelline))},
            backgroundColor = colorResource(id = R.color.caribbeanCurrent)
        )
    }

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
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_center_navigator_app_icon),
                    contentDescription = null,
                    modifier = Modifier.size(260.dp)
                )
                TextField(
                    label = {
                        Text(stringResource(id = R.string.email), color = colorResource(id = R.color.caribbeanCurrent))
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
                        emailState = Pattern.matches(emailValidationRegex, email.toString())
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )

                TextField(
                    label = {
                        Text(stringResource(id = R.string.password), color = colorResource(id = R.color.caribbeanCurrent))
                    },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "",
                                tint = colorResource(id = R.color.caribbeanCurrent))
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
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )

                TextField(
                    label = {
                        Text(stringResource(id = R.string.confirmPassword), color = colorResource(id = R.color.caribbeanCurrent))
                    },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility1) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility1 = !passwordVisibility1
                        }) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "",
                                tint = colorResource(id = R.color.caribbeanCurrent))
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
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        if (email.value.text.trim().isEmpty() and password.value.text.trim().isEmpty()){
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = fillInTheBlanksErrorMessage)
                            }
                            keyboardController?.hide()
                        }
                        else if (password.value.text.trim().isNotEmpty()) {
                            if (password.value.text.trim() != password1.value.text.trim()) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = passwordsDoNotMatchErrorMessage)
                                }
                                keyboardController?.hide()
                            } else if (password.value.text.trim() == password1.value.text.trim()) {
                                auth.createUserWithEmailAndPassword(
                                    email.value.text.trim(),
                                    password.value.text.trim()
                                ).addOnCompleteListener(context){ task ->
                                    if (task.isSuccessful){
                                        navController.navigate("MainPage")
                                        keyboardController?.hide()
                                    }else{
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(message = emailAlreadyRegisteredErrorMessage)
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
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orangePeel)
                        ), enabled = emailState and passwordState and passwordState1

                    ) {
                        Text(text = stringResource(id = R.string.registerButton), color = colorResource(id = R.color.isabelline))
                    }
                }

                Divider(
                    color = colorResource(id = R.color.orangePeel),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = (24.dp))
                )

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    Text(stringResource(id = R.string.doHaveAccountButton), color = colorResource(id = R.color.orangePeel))
                    TextButton(onClick = {
                        navController.navigate("LoginPage")
                        keyboardController?.hide()
                    }) {
                        Text(stringResource(id = R.string.loginButton), color = colorResource(id = R.color.caribbeanCurrent))
                    }
                }
            }
        }, backgroundColor = colorResource(id = R.color.isabelline)
    )
}