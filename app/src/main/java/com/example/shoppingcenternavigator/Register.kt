package com.example.shoppingcenternavigator

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Visibility
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@Composable
fun Register(context: ComponentActivity, navController: NavController) {
    val auth = Firebase.auth
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val alertDialog = remember { mutableStateOf(value = true) }

    if (alertDialog.value){
        AlertDialog(
            onDismissRequest = { alertDialog.value = false },
            text = { Text(text = "Geçerli bir e-posta ve en az 6 haneli bir şifre giriniz.",
                color = colorResource(id = R.color.isabelline), fontSize = 18.sp) },
            confirmButton = { Text(text = "Tamam",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable { alertDialog.value = false },
                color = colorResource(id = R.color.isabelline))},
            backgroundColor = colorResource(id = R.color.caribbeanCurrent)
        )
    }

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
                        Modifier.size(50.dp),
                    )
                }
            },
                backgroundColor = colorResource(id = R.color.moonstone))
        },
        content = {
            val email  = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }
            val password1 = remember { mutableStateOf(TextFieldValue()) }
            var passwordVisibility by rememberSaveable { mutableStateOf(value = false) }
            var passwordVisibility1 by rememberSaveable { mutableStateOf(value = false) }
            var emailState by remember { mutableStateOf(false) }
            var passwordState by remember { mutableStateOf(false) }
            var passwordState1 by remember { mutableStateOf(false) }
            val emailValidationRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.com)(.{1,})"


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
                        emailState = Pattern.matches(emailValidationRegex, email.toString())
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
                        passwordState = password.value.text.length >= 6
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )

                TextField(
                    label = {
                        Text("Tekrar şifre giriniz", color = colorResource(id = R.color.caribbeanCurrent))
                    },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility1) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility1 = !passwordVisibility1
                        }) {
                            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    value = password1.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        password1.value = it
                        passwordState1 = password.value.text.length >= 6
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.caribbeanCurrent),
                        focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent))
                )
                Button(onClick = {
                    if (email.value.text.trim().isEmpty() and password.value.text.trim().isEmpty()){
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = "Lütfen boş alanları doldurunuz")
                        }
                    }
                    else if (password.value.text.trim().isNotEmpty()) {
                        if (password.value.text.trim() != password1.value.text.trim()) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = "Şifreler eşleşmiyor.")
                            }
                        } else if (password.value.text.trim() == password1.value.text.trim()) {
                            auth.createUserWithEmailAndPassword(
                                email.value.text.trim(),
                                password.value.text.trim()
                            ).addOnCompleteListener(context){ task ->
                                if (task.isSuccessful){
                                    navController.navigate("MainPage")
                                }else{
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "Bu e-posta adresi zaten kayıtlı.")
                                    }
                                }
                            }
                        }
                    }
                },
                    Modifier.size(200.dp,50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.orangePeel)
                    ), enabled = emailState and passwordState and passwordState1

                ){
                    Text(text = "Kayıt Ol", color = colorResource(id = R.color.isabelline))
                }
            }
        },
        backgroundColor = colorResource(id = R.color.isabelline)
    )
}