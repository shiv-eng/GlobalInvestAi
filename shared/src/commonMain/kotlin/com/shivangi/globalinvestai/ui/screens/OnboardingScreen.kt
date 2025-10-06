package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shivangi.globalinvestai.ui.components.SparklesIcon
import com.shivangi.globalinvestai.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object OnboardingScreen : Screen {

    data class Message(val text: String, val sender: String)

    private val onboardingFlow = listOf(
        "Hi! I'm your AI investment assistant. To start, what kind of companies or industries are you passionate about?" to listOf("Technology", "Healthcare", "Sustainable Energy", "Finance"),
        "That's great! Now, let's talk about risk. How would you feel if your investment portfolio dropped by 10% in a month?" to listOf("I'd be very concerned", "A little worried is normal", "I see it as a buying opportunity"),
        "Understood. We're building a profile that matches your style. What's your primary financial goal for investing?" to listOf("Long-term growth", "Regular income", "Saving for a big purchase"),
        "Perfect. One last thing. Are you new to investing, or do you have some experience?" to listOf("I'm new to this", "I have some experience", "I'm an expert")
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val messages = remember { mutableStateListOf<Message>() }
        var currentStep by remember { mutableStateOf(0) }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var optionsEnabled by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            if (messages.isEmpty()) {
                messages.add(Message(onboardingFlow[0].first, "ai"))
            }
        }

        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }

        fun handleOptionClick(option: String) {
            if (!optionsEnabled) return

            optionsEnabled = false
            messages.add(Message(option, "user"))

            coroutineScope.launch {
                delay(1000)

                // Check if this was the last question
                if (currentStep >= onboardingFlow.size - 1) {
                    // All questions answered
                    messages.add(Message("Thank you! Your investor profile is ready. Let's create your secure account to get started.", "ai"))
                    delay(1500)
                    navigator.push(RegistrationScreen)
                } else {
                    // Move to next question
                    val nextStep = currentStep + 1
                    messages.add(Message(onboardingFlow[nextStep].first, "ai"))
                    currentStep = nextStep
                    optionsEnabled = true
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("Welcome! Let's get to know you.")
                            Text("Our AI will guide you through a quick setup.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    },
                )
            },
            bottomBar = {
                if (currentStep < onboardingFlow.size && optionsEnabled) {
                    Surface(shadowElevation = 8.dp) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding()) {
                            onboardingFlow[currentStep].second.forEach { option ->
                                Button(
                                    onClick = { handleOptionClick(option) },
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                                ) {
                                    Text(option, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                } else if (!optionsEnabled) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp).navigationBarsPadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Processing...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }
        }
    }

    @Composable
    fun MessageBubble(message: Message) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = if (message.sender == "user") Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            if (message.sender == "ai") {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Primary, CircleShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(SparklesIcon, contentDescription = "AI Icon", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Surface(
                color = if (message.sender == "user") Primary else MaterialTheme.colorScheme.surface,
                contentColor = if (message.sender == "user") Color.White else MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp,
                border = if (message.sender == "ai") BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp)
                )
            }
            if (message.sender == "user") {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}