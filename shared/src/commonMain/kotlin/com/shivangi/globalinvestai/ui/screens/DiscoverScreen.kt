package com.shivangi.globalinvestai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

object DiscoverScreen : Screen {
    @Composable
    override fun Content() {
        // Placeholder implementation
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Discover Screen")
        }
    }
}