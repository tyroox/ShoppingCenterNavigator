package com.example.shoppingcenternavigator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Settings() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentLanguage = configuration.locales[0]
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    var expanded by remember { mutableStateOf(false) }

    // Rest of your settings UI

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
            Text(text = selectedLanguage.language.toUpperCase())
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

