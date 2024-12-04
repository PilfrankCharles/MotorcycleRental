package com.example.motorcyclerental

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelledBookingsScreen(navController: NavController) {
    val cancelledBookings = BookingManager.cancelledBookingRecords
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            BookingManager.fetchCancelledBookings(
                onSuccess = { isLoading = false },
                onFailure = { e ->
                    errorMessage = "Failed to fetch cancelled bookings: ${e.message}"
                    isLoading = false
                }
            )
        } catch (e: Exception) {
            errorMessage = "Failed to fetch cancelled bookings: ${e.message}"
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Cancelled Bookings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                    cancelledBookings.isEmpty() -> {
                        Text(
                            text = "No cancelled bookings found.",
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
                            cancelledBookings.forEach { booking ->
                                BookingItem(
                                    booking = booking,
                                    isCancelled = true
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    )
}

// Fetch cancelled bookings from Firestore
private fun fetchCancelledBookingsFromFirestore(onBookingsFetched: (List<BookingRecord>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val bookingsRef = db.collection("cancelled_bookings")

    // Fetch cancelled bookings from Firestore
    bookingsRef.get()
        .addOnSuccessListener { documents ->
            val bookingsList = documents.mapNotNull { document ->
                val data = document.data
                // Use BookingRecord.fromMap to create BookingRecord object from Firestore data
                BookingRecord.fromMap(data, document.id)
            }
            onBookingsFetched(bookingsList)
        }
        .addOnFailureListener { exception ->
            // Handle error (e.g., log it or show an error message)
            onBookingsFetched(emptyList())
        }
}
