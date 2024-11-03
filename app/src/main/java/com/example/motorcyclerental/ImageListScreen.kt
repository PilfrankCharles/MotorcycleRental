package com.example.motorcyclerental

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun ImageListScreen(brandName: String, navController: NavController) {
    val imageDescriptions = when (brandName) {
        "Honda" -> listOf(
            Pair(R.drawable.click125i, "Honda Click 125i"),
            Pair(R.drawable.beat125, "Honda BeAT"),
            Pair(R.drawable.wave110, "Honda Wave 110"),
            Pair(R.drawable.xrm125, "Honda XRM 125"),
            Pair(R.drawable.crf150, "Honda CRF 150")
        )
        "Yamaha" -> listOf(
            Pair(R.drawable.nmax, "Yamaha NMAX"),
            Pair(R.drawable.soul, "Yamaha Mio Soul i 125"),
            Pair(R.drawable.yzf, "Yamaha YZF-R15"),
            Pair(R.drawable.mt15, "Yamaha MT-15"),
            Pair(R.drawable.fzs25, "Yamaha FZS 25")
        )
        "Suzuki" -> listOf(
            Pair(R.drawable.raider150, "Suzuki Raider R150"),
            Pair(R.drawable.gsxr150, "Suzuki GSX-R150"),
            Pair(R.drawable.address, "Suzuki Address"),
            Pair(R.drawable.burgman, "Suzuki Burgman Street 125"),
            Pair(R.drawable.gixxer155, "Suzuki Gixxer 155")
        )
        "Kawasaki" -> listOf(
            Pair(R.drawable.ninja400, "Kawasaki Ninja 400"),
            Pair(R.drawable.z400, "Kawasaki Z400"),
            Pair(R.drawable.klx150, "Kawasaki KLX 150"),
            Pair(R.drawable.versyx300, "Kawasaki Versys-X 300"),
            Pair(R.drawable.kx65, "Kawasaki KX 65")
        )
        else -> listOf()
    }

    // Rates for each bike model
    val rates = when (brandName) {
        "Honda" -> listOf(
            "Daily rate: ₱600\nWeekly rate: ₱3,500\nMonthly rate: ₱10,000",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,000",
            "Daily rate: ₱450\nWeekly rate: ₱2,800\nMonthly rate: ₱7,500",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,000",
            "Daily rate: ₱800\nWeekly rate: ₱4,500\nMonthly rate: ₱15,000"
        )
        "Yamaha" -> listOf(
            "Daily rate: ₱700\nWeekly rate: ₱4,000\nMonthly rate: ₱12,000",
            "Daily rate: ₱600\nWeekly rate: ₱3,500\nMonthly rate: ₱10,000",
            "Daily rate: ₱800\nWeekly rate: ₱4,500\nMonthly rate: ₱13,000",
            "Daily rate: ₱800\nWeekly rate: ₱4,500\nMonthly rate: ₱13,000",
            "Daily rate: ₱750\nWeekly rate: ₱4,200\nMonthly rate: ₱11,500"
        )
        "Suzuki" -> listOf(
            "Daily rate: ₱600\nWeekly rate: ₱3,500\nMonthly rate: ₱10,000",
            "Daily rate: ₱800\nWeekly rate: ₱4,500\nMonthly rate: ₱13,000",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,500",
            "Daily rate: ₱700\nWeekly rate: ₱4,000\nMonthly rate: ₱11,000",
            "Daily rate: ₱600\nWeekly rate: ₱3,500\nMonthly rate: ₱10,000"
        )
        "Kawasaki" -> listOf(
            "Daily rate: ₱900\nWeekly rate: ₱5,000\nMonthly rate: ₱15,000",
            "Daily rate: ₱850\nWeekly rate: ₱4,800\nMonthly rate: ₱14,500",
            "Daily rate: ₱700\nWeekly rate: ₱4,000\nMonthly rate: ₱11,000",
            "Daily rate: ₱850\nWeekly rate: ₱4,800\nMonthly rate: ₱14,500",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,500"
        )
        else -> listOf()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween // Arrange items to occupy available space
    ) {
        // Header
        Text(
            text = "$brandName Models",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of images and rates
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(imageDescriptions.zip(rates)) { (image, rate) ->
                val (imageResId, description) = image
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = description,
                        modifier = Modifier
                            .size(180.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = rate,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Button(
                            onClick = {
                                navController.navigate("BookingScreen/${description}/${rate}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                        ) {
                            Text(
                                text = "Book Now",
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Bottom Navigation
        BottomNavigation(navController)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigate(route = "MainScreen") },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home Icon",
                tint = Color.Black
            )
        }

        IconButton(
            onClick = { /* TODO: Implement functionality */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date Range Icon",
                tint = Color.Black
            )
        }

        IconButton(
            onClick = { /* TODO: Implement functionality */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = "Place Icon",
                tint = Color.Black
            )
        }

        IconButton(
            onClick = { /* TODO: Implement functionality */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Person Icon",
                tint = Color.Black
            )
        }
    }
}
