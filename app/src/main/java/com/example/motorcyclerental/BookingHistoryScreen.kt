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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            bookings.forEach { booking ->
                BookingItem(booking = booking)
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { BookingManager.clearAllBookings() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1877F2)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .padding(bottom = 42.dp)
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
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text("Bike Name: ${booking.bikeName}", fontWeight = FontWeight.Bold)
        Text("Rate Type: ${booking.rateType}")
        Text("Total Cost: ${booking.totalCost}")
        Text("Date: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(booking.timestamp)}")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookingHistoryScreen() {
    val navController = rememberNavController()
    MotorcycleRentalTheme {
        BookingHistoryScreen(navController = navController)
    }
}
