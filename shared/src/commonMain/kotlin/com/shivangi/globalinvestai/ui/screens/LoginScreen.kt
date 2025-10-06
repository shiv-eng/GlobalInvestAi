package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.ui.components.SparklesIcon
import com.shivangi.globalinvestai.ui.theme.Primary
import com.shivangi.globalinvestai.ui.theme.TextPrimary
import com.shivangi.globalinvestai.ui.theme.TextSecondary

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }

        fun validate(): Boolean {
            emailError = if (email.isBlank() || !email.contains("@")) "Please enter a valid email" else null
            passwordError = if (password.length < 6) "Password must be at least 6 characters" else null
            return emailError == null && passwordError == null
        }

        Surface(color = Primary, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.5f))

                // --- Header ---
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White.copy(alpha = 0.2f), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = SparklesIcon,
                            contentDescription = "App Icon",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Global Invest AI",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        "Your AI-powered gateway to global markets.",
                        fontSize = 18.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // --- Input Fields ---
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; emailError = null },
                        label = { Text("Email") },
                        isError = emailError != null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.White.copy(alpha = 0.1f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White.copy(alpha = 0.7f),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            errorSupportingTextColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError != null) {
                        Text(emailError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; passwordError = null },
                        label = { Text("Password") },
                        isError = passwordError != null,
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.White.copy(alpha = 0.1f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White.copy(alpha = 0.7f),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            errorSupportingTextColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (passwordError != null) {
                        Text(passwordError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                    }
                }


                Spacer(modifier = Modifier.height(32.dp))

                // --- Buttons ---
                Button(
                    onClick = {
                        if (validate()) {
                            navigator.replaceAll(HomeScreen)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Sign In", color = Primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { navigator.push(OnboardingScreen) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                ) {
                    Text("Sign Up", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}

