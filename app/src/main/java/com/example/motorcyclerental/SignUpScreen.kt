package com.example.motorcyclerental

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val fullName = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

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
                text = "Moto Rent",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create Account",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fill in the details below",
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = fullName.value,
                onValueChange = { fullName.value = it },
                label = { Text("Full Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, LightGray, RoundedCornerShape(12.dp)),
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, LightGray, RoundedCornerShape(12.dp)),
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(password = password, label = "Password")

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(password = confirmPassword, label = "Confirm Password")

            Spacer(modifier = Modifier.height(24.dp))

            val auth = FirebaseAuth.getInstance()

            Button(
                onClick = {
                    if (fullName.value.text.isNotEmpty() && email.value.text.isNotEmpty() &&
                        password.value.text.isNotEmpty() && confirmPassword.value.text.isNotEmpty()
                    ) {
                        if (password.value.text == confirmPassword.value.text) {
                            auth.createUserWithEmailAndPassword(
                                email.value.text.trim(),
                                password.value.text.trim()
                            ).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = FirebaseAuth.getInstance().currentUser
                                    val db = FirebaseFirestore.getInstance()

                                    // Check if user is not null before saving to Firestore
                                    if (user != null) {
                                        val userRef = db.collection("users").document(user.uid)

                                        // Save the user document with their UID and any other information
                                        userRef.set(
                                            mapOf(
                                                "uid" to user.uid,
                                                "email" to user.email,
                                                "name" to fullName.value.text // Storing full name
                                            )
                                        ).addOnSuccessListener {
                                            // Increment the active user count in Firestore
                                            val activeUsersRef = db.collection("statistics").document("active_users_count")
                                            activeUsersRef.get().addOnSuccessListener { document ->
                                                val currentCount = document.getLong("count")?.toInt() ?: 0
                                                activeUsersRef.update("count", currentCount + 1)
                                            }

                                            Toast.makeText(
                                                context,
                                                "User details saved successfully!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener { e ->
                                            Toast.makeText(
                                                context,
                                                "Failed to save user details: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    Toast.makeText(
                                        context,
                                        "Sign-up successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("LoginScreen")
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Sign-up failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF316FF6)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Sign In",
                    color = Color(0xFF316FF6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("LoginScreen") {
                            popUpTo("LoginScreen") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: MutableState<TextFieldValue>, label: String) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    val togglePasswordVisibility: () -> Unit = {
        isPasswordVisible.value = !isPasswordVisible.value
    }

    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, LightGray, RoundedCornerShape(12.dp)),
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


