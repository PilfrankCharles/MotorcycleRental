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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(navController: NavController) {
    val bookings = BookingManager.bookingRecords
    val cancelledBookings = remember { mutableStateListOf<BookingRecord>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            BookingManager.fetchAllBookings(
                onSuccess = { isLoading = false },
                onFailure = { e ->
                    errorMessage = "Failed to fetch bookings: ${e.message}"
                    isLoading = false
                }
            )
        } catch (e: Exception) {
            errorMessage = "Failed to fetch bookings: ${e.message}"
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Booking History", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0))
                    .padding(innerPadding)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "Unknown error occurred.",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                    bookings.isEmpty() -> {
                        Text(
                            text = "No booking history found.",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            bookings.forEach { booking ->
                                val isCancelled = cancelledBookings.contains(booking)

                                BookingItem(
                                    booking = booking,
                                    onCancel = {
                                        BookingManager.cancelBooking(booking.id,
                                            onSuccess = {
                                                cancelledBookings.add(booking)
                                                bookings.remove(booking)
                                                BookingManager.addCancelledBooking(booking) // Add to cancelled list
                                            },
                                            onFailure = { e ->
                                                errorMessage = "Failed to cancel booking: ${e.message}"
                                            }
                                        )
                                    },
                                    isCancelled = isCancelled
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        isLoading = true
                                        errorMessage = null
                                        BookingManager.clearAllBookings(
                                            onSuccess = {
                                                bookings.clear()
                                                isLoading = false
                                            },
                                            onFailure = { e ->
                                                errorMessage = "Failed to clear bookings: ${e.message}"
                                                isLoading = false
                                            }
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Text("Clear All Booking History", color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    navController.navigate("CancelledBookingsScreen")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Text("View Cancelled Bookings", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BookingItem(
    booking: BookingRecord,
    onCancel: (() -> Unit)? = null,
    isCancelled: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .padding(16.dp)
    ) {
        Text("Bike Name: ${booking.bikeName}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Rate Type: ${booking.rateType}", fontSize = 14.sp)
        Text("Total Cost: ${booking.totalCost}", fontSize = 14.sp)
        Text(
            "Date: ${
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(booking.timestamp)
            }",
            fontSize = 12.sp
        )

        if (!isCancelled && onCancel != null) {
            Button(
                onClick = {
                    onCancel()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel Booking", color = Color.White)
            }
        } else if (isCancelled) {
            Text(
                text = "This booking has been cancelled.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Red
            )
        }
    }
}