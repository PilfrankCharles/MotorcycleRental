package com.example.motorcyclerental

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavController) {
    var motorcycleCount by remember { mutableStateOf(0) }
    var activeUserCount by remember { mutableStateOf(0) }
    var totalSignUpCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        motorcycleCount = fetchMotorcycleImageCount()
        activeUserCount = fetchActiveUserCount()
        totalSignUpCount = fetchTotalSignUpCount()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = " ")
            },
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                AdminCard(title = "Motorcycle", value = motorcycleCount.toString(), modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminCard(title = "Total Sign-ups", value = totalSignUpCount.toString(), modifier = Modifier.weight(1f))
            }
        }

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

suspend fun fetchMotorcycleImageCount(): Int {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child("motorcycles")

    return try {
        val result = storageRef.listAll().await()
        result.items.size
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

suspend fun fetchActiveUserCount(): Int {
    val db = FirebaseFirestore.getInstance()
    val usersRef = db.collection("users")
    return try {
        val snapshot = usersRef.whereEqualTo("isActive", true).get().await()
        snapshot.size()
    } catch (e: Exception) {
        0
    }
}

suspend fun fetchTotalSignUpCount(): Int {
    val db = FirebaseFirestore.getInstance()
    val usersRef = db.collection("users")
    return try {
        val snapshot = usersRef.get().await()
        snapshot.size()
    } catch (e: Exception) {
        0
    }
}
