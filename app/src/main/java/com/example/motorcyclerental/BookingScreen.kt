package com.example.motorcyclerental

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(bikeName: String, rate: String, navController: NavController) {
    val context = LocalContext.current
    var selectedRate by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.white_bg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {

            TopAppBar(
                title = { Spacer(modifier = Modifier.width(0.dp)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            // Main content column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Model: $bikeName",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 32.dp)
                    )

                    Text(
                        text = "$rate",
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    RateOption("Daily Rate", selectedRate == "Daily Rate") {
                        selectedRate = "Daily Rate"
                    }
                    RateOption("Weekly Rate", selectedRate == "Weekly Rate") {
                        selectedRate = "Weekly Rate"
                    }
                    RateOption("Monthly Rate", selectedRate == "Monthly Rate") {
                        selectedRate = "Monthly Rate"
                    }

                    if (selectedRate.isNotEmpty()) {
                        Text(
                            text = "Selected: $selectedRate",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 80.dp)
                ) {
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF316FF6),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Confirm Booking", fontSize = 16.sp)
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Confirm Booking") },
                text = { Text(text = "Are you sure you want to confirm the booking?") },
                confirmButton = {
                    TextButton(onClick = {
                        // Call BookingManager to add booking (update this with Firebase functionality)
                        BookingManager.addBooking(
                            bikeName = bikeName,
                            rateType = selectedRate,
                            totalCost = rate,
                            onSuccess = {
                                showDialog = false
                                navController.navigate("BookingHistoryScreen") {
                                    popUpTo("BookingScreen") { inclusive = true }
                                }
                            },
                            onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Failed to confirm booking: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showDialog = false
                            }
                        )
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun RateOption(
    label: String,
    isSelected: Boolean,
    onSelectedChange: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.LightGray else Color.Transparent
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = onSelectedChange)
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelectedChange,
            modifier = Modifier.padding(4.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF316FF6),
                unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
