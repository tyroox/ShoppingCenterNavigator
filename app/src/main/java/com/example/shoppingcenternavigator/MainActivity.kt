package com.example.shoppingcenternavigator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingcenternavigator.ui.theme.ShoppingCenterNavigatorTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            ShoppingCenterNavigatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.isabelline)

                ) {
                    NextPage()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NextPage(){
    val user = Firebase.auth.currentUser
    val navController = rememberNavController()
    if (user != null) {
        NavHost(navController = navController,  startDestination = "MainPage"){
            composable("LoginPage"){
                Login(context = ComponentActivity(), navController = navController)
            }
            composable("ForgotPasswordPage"){
                ForgotPassword(context = ComponentActivity(), navController = navController)
            }
            composable("RegisterPage"){
                Register(context = ComponentActivity(), navController = navController)
            }
            composable("MainPage"){
                MainPage(navController = navController)
            }
            composable("GPS"){
                GPS()
            }
            composable("MallSearchBar"){
                MallSearchBar(navController = navController)
            }
            composable("WayFindingAlgorithm"){
                WayFindingAlgorithm()
            }
        }
    } else {
        NavHost(navController = navController,  startDestination = "LoginPage"){
            composable("LoginPage"){
                Login(context = ComponentActivity(), navController = navController)
            }
            composable("ForgotPasswordPage"){
                ForgotPassword(context = ComponentActivity(), navController = navController)
            }
            composable("RegisterPage"){
                Register(context = ComponentActivity(),navController = navController)
            }
            composable("MainPage"){
                MainPage(navController = navController)
            }
            composable("GPS"){
                GPS()
            }
            composable("MallSearchBar"){
                MallSearchBar(navController = navController)
            }
            composable("WayFindingAlgorithm"){
                WayFindingAlgorithm()
            }
        }
    }
}
