package com.example.motorcyclerental

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri

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

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.background_bg_2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "$brandName Models",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(imageDescriptions.zip(rates)) { (image, rate) ->
                    val (imageResId, description) = image
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .border(1.dp, Color.Black, CutCornerShape(8.dp))
                                .padding(2.dp)
                                .shadow(3.dp, CutCornerShape(8.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(165.dp) // Adjust ni sa image size manually
                                    .align(Alignment.Center)
                            ) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = description,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

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
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            Button(
                                onClick = {
                                    val encodedDescription = description.toUri().toString()
                                    val encodedRate = rate.toUri().toString()
                                    navController.navigate("BookingScreen/$encodedDescription/$encodedRate")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6))
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
        }
    }
}
