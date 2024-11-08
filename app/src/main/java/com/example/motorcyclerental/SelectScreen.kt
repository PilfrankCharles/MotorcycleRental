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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Foreground content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = buildAnnotatedString {
                    append("BOOK A")
                    withStyle(style = SpanStyle(color = Color(0xFF316FF6))) {
                        append(" RIDE!")
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(42.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(4) { index ->
                        val imageResource = when (index) {
                            0 -> R.drawable.honda_logo_nbg
                            1 -> R.drawable.yamaha_logo_nbg
                            2 -> R.drawable.suzuki_logo_nbg
                            3 -> R.drawable.kawasaki_logo_nbg
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
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(320.dp)
                                    .clip(CutCornerShape(8.dp))
                                    .shadow(2.dp, shape = CutCornerShape(8.dp))
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
                                            .size(160.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Text(
                                        text = descriptions[index],
                                        fontSize = 12.sp,
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
    }
}
