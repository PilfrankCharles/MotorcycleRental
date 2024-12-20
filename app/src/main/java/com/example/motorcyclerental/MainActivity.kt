package com.example.motorcyclerental

import ManageMotorcycleDetailsScreen
import SignUpScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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

                composable("BookingHistoryScreen") {
                    BookingHistoryScreen(navController)
                }

                composable("CancelledBookingsScreen") {
                    CancelledBookingsScreen(navController)
                }

                composable("LoginScreen") {
                    LoginScreen(navController)
                }

                composable("SignUpScreen") {
                    SignUpScreen(navController)
                }

                composable("AdminDashboard") {
                    AdminDashboard(navController)
                }

                composable("ManageMotorcycleDetails") {
                    ManageMotorcycleDetailsScreen()
                }

                composable("ManageBookingScreen") {
                    ManageBookingScreen(navController)
                }

                composable("ApproveBookingScreen") {
                    ApproveBookingScreen(navController)
                }

                composable("RejectBookingScreen") {
                    RejectBookingScreen(navController)
                }
            }
        }
    }
}
