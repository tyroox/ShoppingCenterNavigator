package com.example.shoppingcenternavigator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Settings() {
    NavGraph()
}
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "settings") {
        composable("settings") {
            Settings(onFAQClick = { navController.navigate("faq") },
                onUserSettingsClick = { navController.navigate("userSettings") })
        }
        composable("faq") {
            FAQPage()
        }
        composable("userSettings") {
            User(navController = navController)
        }

    }
}

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Settings(onFAQClick: () -> Unit, onUserSettingsClick: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentLanguage = configuration.locales[0]
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onFAQClick,modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orangePeel))) {
            Text(text = "Sıkça Sorulan Sorular (S.S.S)")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onUserSettingsClick, modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orangePeel))) {
            Text(text = "Kullanıcı Ayarları")
        }


        // Language selection
        Box {
            Row {
                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.language),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                        tint = caribbeanCurrent
                        // You can customize the appearance of the icon here
                    )
                }
                Text(text = selectedLanguage.language.toUpperCase(), modifier = Modifier.align(
                    Alignment.CenterVertically
                ))
            }

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
                    content = { Text(text = "EN") }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedLanguage = Locale("tr")
                        updateLocale(configuration, context, selectedLanguage)
                        expanded = false
                        recreateActivity(context)

                    },
                    // Turkish option
                    content = { Text(text = "TR") }
                )
            }
        }
    }

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

