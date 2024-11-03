package com.example.motorcyclerental

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BookingScreen(bikeName: String, rate: String, navController: NavController) {
    var isDailyChecked by remember { mutableStateOf(false) }
    var isWeeklyChecked by remember { mutableStateOf(false) }
    var isMonthlyChecked by remember { mutableStateOf(false) }

    // Set background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, // Center vertically
            modifier = Modifier.fillMaxHeight() // Fill the height of the Box
        ) {
            Text(
                text = "Booking Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Model: $bikeName",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "$rate",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Checkboxes for selecting rates
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isDailyChecked,
                    onCheckedChange = { isDailyChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black
                    )
                )
                Text(text = "Daily Rate", modifier = Modifier.padding(start = 8.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isWeeklyChecked,
                    onCheckedChange = { isWeeklyChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                    )
                )
                Text(text = "Weekly Rate", modifier = Modifier.padding(start = 8.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Checkbox(
                    checked = isMonthlyChecked,
                    onCheckedChange = { isMonthlyChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                    )
                )
                Text(text = "Monthly Rate", modifier = Modifier.padding(start = 8.dp))
            }

            Button(
                onClick = {
                    navController.navigate("MainScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
            ) {
                Text(text = "Confirm Booking")
            }
        }
    }
}
