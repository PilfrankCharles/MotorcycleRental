package com.example.motorcyclerental

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(navController: NavController) {
    // List of bookings retrieved from Firebase
    val bookings = remember { mutableStateListOf<BookingRecord>() }

    // Fetch bookings from Firestore
    LaunchedEffect(Unit) {
        val fetchedBookings = BookingManager.getAllBookings()
        bookings.addAll(fetchedBookings)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            TopAppBar(
                title = {
                    Text("Booking History", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Displaying all the bookings in a list
            bookings.forEach { booking ->
                BookingItem(booking = booking)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to clear all booking history
            Button(
                onClick = { BookingManager.clearAllBookings() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1877F2)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 42.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Clear All Booking History", color = Color.White)
            }
        }
    }
}

@Composable
fun BookingItem(booking: BookingRecord) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Bike Name: ${booking.bikeName}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Rate Type: ${booking.rateType}", fontSize = 14.sp)
        Text("Total Cost: ${booking.totalCost}", fontSize = 14.sp)
        Text("Date: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(booking.timestamp)}", fontSize = 12.sp)
    }
}
