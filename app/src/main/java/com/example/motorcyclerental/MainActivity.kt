package com.example.motorcyclerental


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.*
import com.example.motorcyclerental.ImageListScreen
import com.example.motorcyclerental.MainScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                composable("imageList/{brandName}") { backStackEntry ->
                    val brandName = backStackEntry.arguments?.getString("brandName") ?: ""
                    ImageListScreen(brandName, navController)
                }
            }
        }
    }
}

