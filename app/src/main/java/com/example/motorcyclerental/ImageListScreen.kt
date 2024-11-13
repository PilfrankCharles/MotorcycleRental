package com.example.motorcyclerental

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.core.net.toUri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(brandName: String, navController: NavController) {
    val imageDescriptions = when (brandName) {
        "Honda" -> listOf(
            Pair(R.drawable.click125i, "Honda Click 125"),
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

    val rates = when (brandName) {
        "Honda" -> listOf(
            "Daily rate: ₱600\nWeekly rate: ₱3,500\nMonthly rate: ₱10,000",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,000",
            "Daily rate: ₱450\nWeekly rate: ₱2,800\nMonthly rate: ₱7,500",
            "Daily rate: ₱500\nWeekly rate: ₱3,000\nMonthly rate: ₱8,000",
            "Daily rate: ₱800\nWeekly rate: ₱4,500\nMonthly rate: ₱15,000"
        )
        // Add other brand rates here as needed
        else -> listOf()
    }

    var selectedItem by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.white_bg),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text(text = "$brandName Models") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(imageDescriptions.zip(rates)) { (image, rate) ->
                        val (imageResId, description) = image
                        var showRates by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .height(250.dp)
                                .width(150.dp)
                                .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
                                .padding(2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = description,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(18.dp))
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = description,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                Button(
                                    onClick = { showRates = !showRates },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "View Details",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }

                                AnimatedVisibility(
                                    visible = showRates,
                                    enter = expandVertically(),
                                    exit = shrinkVertically()
                                ) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = rate,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                        Button(
                                            onClick = {
                                                val encodedDescription = description.toUri().toString()
                                                val encodedRate = rate.toUri().toString()
                                                // Navigate to BookingScreen with the encoded description and rate
                                                navController.navigate("BookingScreen/$encodedDescription/$encodedRate")
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6)),
                                            modifier = Modifier.padding(top = 8.dp)
                                        ) {
                                            Text(
                                                text = "Proceed to Booking",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        var selectedItem by remember { mutableStateOf(0) }

        NavigationBar(
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = false,
                onClick = {
                    navController.navigate("MainScreen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Favorite, contentDescription = "Saved") },
                label = { Text("Saved") },
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
                label = { Text("Notification") },
                selected = selectedItem == 2,
                onClick = { selectedItem = 2 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = selectedItem == 3,
                onClick = { selectedItem = 3 }
            )
        }
    }
}
