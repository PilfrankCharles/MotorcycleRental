package com.example.motorcyclerental

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

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
            CenterAlignedTopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.height(72.dp)
            )

            Text(
                text = "Moto Rent",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

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

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(password = password)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val enteredEmail = email.value.text
                    val enteredPassword = password.value.text

                    if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(enteredEmail, enteredPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                                    navController.navigate("SelectScreen")
                                } else {
                                    val exceptionMessage = task.exception?.message ?: "Authentication failed"
                                    when {
                                        exceptionMessage.contains("no user record", ignoreCase = true) -> {
                                            Toast.makeText(context, "Your email is wrong.", Toast.LENGTH_SHORT).show()
                                        }
                                        exceptionMessage.contains("password is invalid", ignoreCase = true) -> {
                                            Toast.makeText(context, "Your password is wrong.", Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                    } else {
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

            Text(
                text = "Forgot your password?",
                color = Color(0xFF316FF6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickable {}
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                        navController.navigate("SignUpScreen")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: MutableState<TextFieldValue>) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    val togglePasswordVisibility: () -> Unit = {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
        textStyle = TextStyle(fontSize = 18.sp),
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(onClick = togglePasswordVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    )
}
