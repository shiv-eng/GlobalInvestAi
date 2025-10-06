package com.shivangi.globalinvestai.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)