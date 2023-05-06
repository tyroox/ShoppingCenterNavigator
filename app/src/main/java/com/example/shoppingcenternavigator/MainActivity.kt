package com.example.shoppingcenternavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingcenternavigator.ui.theme.ShoppingCenterNavigatorTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

@Composable
fun NextPage(){
    val user = Firebase.auth.currentUser
    val navController = rememberNavController()
    if (user != null) {
        NavHost(navController = navController,  startDestination = "MainPage"){
            composable("StartPage"){
                StartPage(navController = navController)
            }
            composable("LoginPage"){
                Login(context = ComponentActivity(), navController = navController)
            }
            composable("RegisterPage"){
                Register(context = ComponentActivity(), navController = navController)
            }
            composable("MainPage"){
                MainPage(navController = navController)
            }
        }
    } else {
        NavHost(navController = navController,  startDestination = "StartPage"){
            composable("StartPage"){
                StartPage(navController = navController)
            }
            composable("LoginPage"){
                Login(context = ComponentActivity(), navController = navController)
            }
            composable("RegisterPage"){
                Register(context = ComponentActivity(),navController = navController)
            }
            composable("MainPage"){
                MainPage(navController = navController)
            }
        }
    }
}