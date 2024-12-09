package com.example.motorcyclerental

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavController) {
    // State variables to hold data
    var motorcycleCount by remember { mutableStateOf(0) }
    var activeUserCount by remember { mutableStateOf(0) }
    var totalSignUpCount by remember { mutableStateOf(0) }

    // Fetch data on first composition
    LaunchedEffect(Unit) {
        motorcycleCount = fetchMotorcycleCount()
        activeUserCount = fetchActiveUserCount() // Fetch the count of authenticated users
        totalSignUpCount = fetchTotalSignUpCount() // Fetch the total number of sign-ups
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.height(72.dp)
        )

        Text(
            text = "Admin Dashboard",
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminCard(title = "Total Rentals", value = "120", modifier = Modifier.weight(1f))
                AdminCard(title = "Active Users", value = activeUserCount.toString(), modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminCard(title = "Available", value = "20", modifier = Modifier.weight(1f))
                AdminCard(title = "Motorcycles", value = motorcycleCount.toString(), modifier = Modifier.weight(1f))
            }
        }

        // Add a row for Total Sign-ups
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AdminCard(title = "Total Sign-ups", value = totalSignUpCount.toString(), modifier = Modifier.weight(1f))
        }

        // Correct button placements
        Button(
            onClick = { navController.navigate("ManageMotorcycleDetails") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6))
        ) {
            Text("Manage Motorcycles Details", color = Color.White)
        }

        Button(
            onClick = { navController.navigate("ManageBookingScreen") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6))
        ) {
            Text("Manage Booking", color = Color.White)
        }

        Button(
            onClick = { navController.navigate("ApproveBookingScreen") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6))
        ) {
            Text("Approve Booking", color = Color.White)
        }

        Button(
            onClick = { navController.navigate("RejectBookingScreen") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6))
        ) {
            Text("Reject Booking", color = Color.White)
        }
    }
}

@Composable
fun AdminCard(title: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 16.sp, color = Color.DarkGray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Function to fetch motorcycle count from Firestore
suspend fun fetchMotorcycleCount(): Int {
    val db = FirebaseFirestore.getInstance()
    val motorcyclesRef = db.collection("motorcycles") // Reference to the motorcycles collection
    return try {
        val snapshot = motorcyclesRef.get().await()
        snapshot.size() // Return the number of motorcycles
    } catch (e: Exception) {
        0 // Return 0 if there's an error fetching the data
    }
}

// Fetch total sign-up count from Firestore
suspend fun fetchTotalSignUpCount(): Int {
    val db = FirebaseFirestore.getInstance()
    val usersRef = db.collection("users")
    return try {
        val snapshot = usersRef.get().await()
        snapshot.size() // Return the number of users (sign-ups)
    } catch (e: Exception) {
        0 // Return 0 if there's an error fetching the data
    }
}

// Function to fetch active user count from Firestore
suspend fun fetchActiveUserCount(): Int {
    val db = FirebaseFirestore.getInstance()
    val usersRef = db.collection("users")
    return try {
        val snapshot = usersRef.whereEqualTo("isActive", true).get().await() // Assuming there's an "isActive" field
        snapshot.size() // Return the number of active users
    } catch (e: Exception) {
        0 // Return 0 if there's an error fetching the data
    }
}
