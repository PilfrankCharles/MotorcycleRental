package com.example.motorcyclerental

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        " ",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(60.dp))

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

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    repeat(2) { index ->
                        val imageResource = when (index) {
                            0 -> R.drawable.honda_logo_nbg
                            1 -> R.drawable.yamaha_logo_nbg
                            else -> R.drawable.placeholder
                        }

                        val descriptions = listOf(
                            "Honda – The Power of Dreams",
                            "Yamaha – Revs Your Heart"
                        )

                        val brandName = when (index) {
                            0 -> "Honda"
                            1 -> "Yamaha"
                            else -> "Unknown"
                        }

                        Box(
                            modifier = Modifier
                                .width(172.dp)
                                .height(242.dp)
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(18.dp))
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
                                        .size(110.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Text(
                                    text = descriptions[index],
                                    fontSize = 10.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    repeat(2) { index ->
                        val imageResource = when (index) {
                            0 -> R.drawable.suzuki_logo_nbg
                            1 -> R.drawable.kawasaki_logo_nbg
                            else -> R.drawable.placeholder
                        }

                        val descriptions = listOf(
                            "Suzuki – Way of Life!",
                            "Kawasaki – Let the Good \nTimes Roll"
                        )

                        val brandName = when (index) {
                            0 -> "Suzuki"
                            1 -> "Kawasaki"
                            else -> "Unknown"
                        }

                        Box(
                            modifier = Modifier
                                .width(172.dp)
                                .height(242.dp)
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(18.dp))
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
                                    contentDescription = "Image Placeholder ${index + 3}",
                                    modifier = Modifier
                                        .size(110.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Text(
                                    text = descriptions[index],
                                    fontSize = 10.sp,
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