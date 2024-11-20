package com.example.motorcyclerental

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.ui.layout.ContentScale

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current // Access context to use Toast

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_bg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top Navigation Bar
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
                modifier = Modifier.height(72.dp) // Adjust the height of the TopAppBar here
            )

            // App Header Text
            Text(
                text = "Moto Rent",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // "Welcome Back" Text
            Text(
                text = "Welcome Back",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your details below",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email input field
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") }, // Label for the email input field
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, LightGray, RoundedCornerShape(12.dp)), // Border with rounded corners
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true, // Ensures the email field is a single line
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Transparent background
                    focusedIndicatorColor = Color.Transparent, // Remove the focused indicator line
                    unfocusedIndicatorColor = Color.Transparent // Remove the unfocused indicator line
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Password input field
            PasswordTextField(password = password)

            Spacer(modifier = Modifier.height(24.dp))

            // Sign-In Button
            Button(
                onClick = {
                    // Validate login credentials here (You can integrate authentication logic)
                    if (email.value.text.isNotEmpty() && password.value.text.isNotEmpty()) {
                        // Navigate to the next screen
                        navController.navigate("HomeScreen")
                    } else {
                        // Show a Toast message or error
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forgot Password Text
            Text(
                text = "Forgot your password?",
                color = Color(0xFF316FF6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickable {
                    // Handle forgot password navigation here
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Social Login Options
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Google Button
                Button(
                    onClick = {
                        // Google Sign In
                    },
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .weight(1f)
                        .border(1.dp, LightGray, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "Google Login",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Google",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Facebook Button
                Button(
                    onClick = {
                        // Facebook Sign In
                    },
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .weight(1f)
                        .border(1.dp, LightGray, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_logo),
                        contentDescription = "Facebook Login",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Facebook",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation to Sign-Up page
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Sign Up",
                    color = Color(0xFF316FF6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        // Navigate to Sign-Up screen
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: MutableState<TextFieldValue>) {
    // State to control password visibility
    val isPasswordVisible = remember { mutableStateOf(false) }

    // Function to toggle the password visibility
    val togglePasswordVisibility: () -> Unit = {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") }, // Label for the password input field
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, LightGray, RoundedCornerShape(12.dp)), // Border with rounded corners
        textStyle = TextStyle(fontSize = 18.sp),
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(), // Toggle between showing or masking the password
        singleLine = true, // Ensures the password field is a single line
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent, // Transparent background
            focusedIndicatorColor = Color.Transparent, // Focused indicator color
            unfocusedIndicatorColor = Color.Transparent // Unfocused indicator color
        ),
        trailingIcon = {
            // Icon to toggle password visibility
            IconButton(onClick = togglePasswordVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    )
}