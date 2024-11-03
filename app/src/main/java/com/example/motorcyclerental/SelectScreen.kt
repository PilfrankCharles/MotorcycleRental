package com.example.motorcyclerental

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SelectScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    append("CHOOSE YOUR ")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("RIDE!")
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(4) { index ->
                    val imageResource = when (index) {
                        0 -> R.drawable.honda_logo
                        1 -> R.drawable.yamaha_logo
                        2 -> R.drawable.suzuki_logo
                        3 -> R.drawable.kawasaki_logo
                        else -> R.drawable.placeholder
                    }

                    val descriptions = listOf(
                        "Honda – The Power of Dreams",
                        "Yamaha – Revs Your Heart",
                        "Suzuki – Way of Life!",
                        "Kawasaki – Let the Good Times Roll"
                    )

                    val brandName = when (index) {
                        0 -> "Honda"
                        1 -> "Yamaha"
                        2 -> "Suzuki"
                        3 -> "Kawasaki"
                        else -> "Unknown"
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shadow(1.dp, shape = RoundedCornerShape(12.dp))
                            .clickable {
                                navController.navigate("imageList/$brandName")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = imageResource),
                                contentDescription = "Image Placeholder ${index + 1}",
                                modifier = Modifier
                                    .size(160.dp) // logo sizes ni
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Text(
                                text = descriptions[index],
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
