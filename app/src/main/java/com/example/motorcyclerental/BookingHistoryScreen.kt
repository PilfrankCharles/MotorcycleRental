package com.example.motorcyclerental

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.motorcyclerental.ui.theme.MotorcycleRentalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(navController: NavController) {
    val bookings = BookingManager.getAllBookings()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF0F0F0), // Light background color for the Box
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
            .padding(16.dp) // Padding for the Box content
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp) // Padding to avoid sticking to the top
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

            bookings.forEach { booking ->
                BookingItem(booking = booking)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { BookingManager.clearAllBookings() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1877F2) // Color of the clear button
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 42.dp)
                    .clip(RoundedCornerShape(12.dp)) // Rounded corners for the button
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
            .background(Color.White, shape = RoundedCornerShape(12.dp)) // Rounded corners and white background for each item
            .shadow(5.dp, RoundedCornerShape(12.dp)) // Shadow effect for depth
            .padding(16.dp)
    ) {
        Text("Bike Name: ${booking.bikeName}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Rate Type: ${booking.rateType}", fontSize = 14.sp)
        Text("Total Cost: ${booking.totalCost}", fontSize = 14.sp)
        Text("Date: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(booking.timestamp)}", fontSize = 12.sp)
    }
}
