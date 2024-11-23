package com.example.motorcyclerental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "MainScreen") {

                composable("MainScreen") {
                    MainScreen(navController)
                }

                composable("SelectScreen") {
                    SelectScreen(navController)
                }

                composable(
                    "imageList/{brandName}",
                    arguments = listOf(navArgument("brandName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val brandName = backStackEntry.arguments?.getString("brandName").orEmpty()
                    ImageListScreen(brandName = brandName, navController = navController)
                }

                composable(
                    "BookingScreen/{bikeName}/{rate}",
                    arguments = listOf(
                        navArgument("bikeName") { type = NavType.StringType },
                        navArgument("rate") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val bikeName = backStackEntry.arguments?.getString("bikeName").orEmpty()
                    val rate = backStackEntry.arguments?.getString("rate").orEmpty()
                    BookingScreen(bikeName = bikeName, rate = rate, navController = navController)
                }

                composable("LoginScreen") {
                    LoginScreen(navController)
                }

                composable("SignUpScreen") {
                    SignUpScreen(navController)
                }
            }
        }
    }
}
