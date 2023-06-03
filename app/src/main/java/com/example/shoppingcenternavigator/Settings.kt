package com.example.shoppingcenternavigator

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.purplishPink
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Settings(navController: NavController, selectedItem: MutableState<Int>, context: Context) {
    val configuration = LocalConfiguration.current
    val currentLanguage = configuration.locales[0]
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    var expanded by remember { mutableStateOf(false) }
    val auth = Firebase.auth
    val alertDialog = remember { mutableStateOf(value = false) }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val email = (auth.currentUser?.email).toString()

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
            Surface (modifier = Modifier.fillMaxSize()){
                VerticalGradient()
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {


                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                    Column(modifier = Modifier.padding(bottom = 16.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp) ,horizontalArrangement = Arrangement.Start) {
                            Icon(Icons.Default.Email, contentDescription = "", modifier = Modifier.padding(top = 0.dp, end = 12.dp), tint = Color.White)
                            Text(text = email, color = wineBerry)
                        }
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                        verticalAlignment = CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.language), modifier = Modifier.padding(top = 2.dp, end = 12.dp), contentDescription = "", tint = Color.White)
                        Text(text = "Language Settings", style = MaterialTheme.typography.h5, color = wineBerry)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = selectedLanguage.language.toUpperCase(),
                            color = Color.White,
                            modifier = Modifier.align(CenterVertically)
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "",
                            modifier = Modifier.clickable {
                                expanded = true
                            } ,tint = Color.White)

                        Box(modifier = Modifier.align(Alignment.Bottom)) {
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.wrapContentSize()
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        selectedLanguage = Locale("en")
                                        updateLocale(configuration, context, selectedLanguage)
                                        expanded = false
                                        recreateActivity(context)

                                    },
                                    // English option
                                    content = { Text(text = "EN", color = wineBerry) }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        selectedLanguage = Locale("tr")
                                        updateLocale(configuration, context, selectedLanguage)
                                        expanded = false
                                        recreateActivity(context)

                                    },
                                    // Turkish option
                                    content = { Text(text = "TR", color = wineBerry) }
                                )
                            }
                        }

                    }

                    Divider(color = wineBerry)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable { selectedItem.value = 6 }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.help), contentDescription = "", modifier = Modifier.padding(top = 2.dp, end = 12.dp), tint = Color.White)
                        Text(text = "Help", style = MaterialTheme.typography.h5, color = wineBerry)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.arrow_forward), contentDescription = "", tint = Color.White)
                    }
                    Divider(color = wineBerry)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable { selectedItem.value = 7 }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = "", modifier = Modifier.padding(top = 2.dp, end = 12.dp), tint = Color.White)
                        Text(text = "About", style = MaterialTheme.typography.h5, color = wineBerry)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.arrow_forward), contentDescription = "", tint = Color.White)
                    }
                    Divider(color = wineBerry)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            auth.sendPasswordResetEmail(email.trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Şifre sıfırlama linki e-postanıza gönderildi. Sıfırladıktan sonra giriş yapabilirsiniz."
                                            )
                                        }
                                    } else {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(message = "Sistemde bir hata oluştu daha sonra tekrar deneyiniz.")
                                        }
                                    }
                                }
                                   }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.lock_reset), contentDescription = "", modifier = Modifier.padding(top = 2.dp, end = 12.dp), tint = Color.White)
                            Text(text = "Change Password", style = MaterialTheme.typography.h5, color = wineBerry)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                            Divider(color = wineBerry)
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clickable { alertDialog.value = true }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = CenterVertically) {
                                if (alertDialog.value){
                                    AlertDialog(
                                        onDismissRequest = { alertDialog.value = false },
                                        text = { Text(text = "Çıkış yapmak istediğinize emin misiniz?",
                                            color = wineBerry, fontSize = 18.sp) },
                                        confirmButton = {
                                            Text(text = "Hayır",
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .clickable {
                                                        alertDialog.value = false
                                                    },
                                                color = wineBerry)},
                                        dismissButton = {
                                            Text(text = "Evet",
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .clickable {
                                                        auth.signOut()
                                                        navController.navigate("LoginPage")
                                                    },
                                                color = wineBerry)},
                                        backgroundColor = Color.White
                                    )
                                }
                                Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "", modifier = Modifier.padding(top = 2.dp, end = 12.dp), tint = Color.White)
                                Text(text = "Logout", style = MaterialTheme.typography.h5, color = wineBerry)
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            Divider(color = wineBerry)
                        }
                }
        }
    )
}

fun updateLocale(configuration: Configuration, context: Context, locale: Locale) {
    // Update the app's configuration with the new locale
    configuration.setLocale(locale)
    // Update the context with the new configuration
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}

fun recreateActivity(context: Context) {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(context.packageName)
    val componentName = intent?.component
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    context.startActivity(mainIntent)
}