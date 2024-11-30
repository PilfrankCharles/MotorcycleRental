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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "",
                )
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
            verticalArrangement = Arrangement.spacedBy(16.dp), // Adds vertical padding between rows
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Adds spacing between AdminCards in the row
            ) {
                AdminCard(title = "Total Rentals", value = "120", modifier = Modifier.weight(1f))
                AdminCard(title = "Active Users", value = "45", modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminCard(title = "Available", value = "20", modifier = Modifier.weight(1f))
                AdminCard(title = "Motorcycles", value = "20", modifier = Modifier.weight(1f))
            }
        }


        Button(onClick = { navController.navigate("ManageMotorcycleDetails") }, modifier = Modifier.fillMaxWidth()) {
            Text("Manage Motorcycles Details")
        }
        Button(onClick = { navController.navigate("ManageBookingScreen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Manage Booking")
        }
        Button(onClick = { navController.navigate("ApproveBookingScreen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Approve Booking")
        }
        Button(onClick = { navController.navigate("RejectBookingScreen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Reject Booking")
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


