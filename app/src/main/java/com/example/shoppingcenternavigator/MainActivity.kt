package com.example.shoppingcenternavigator

import android.os.Bundle
import android.provider.Settings.Secure.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingcenternavigator.ui.theme.ShoppingCenterNavigatorTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
private fun getGoogleLoginAuth(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(getString(R.string.gcp_id))
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(this, gso)
}