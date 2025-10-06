package com.shivangi.globalinvestai.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shivangi.globalinvestai.ui.screens.HomeScreen
import com.shivangi.globalinvestai.ui.screens.PortfolioScreen
import com.shivangi.globalinvestai.ui.screens.ProfileScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun BottomNav(navigator: Navigator) {

    val navItems = listOf(
        BottomNavItem("Home", HomeIcon, HomeScreen),
        BottomNavItem("Portfolio", PortfolioIcon, PortfolioScreen),
        BottomNavItem("Profile", UserIcon, ProfileScreen)
    )

    NavigationBar {
        navItems.forEach { item ->
            val isSelected = navigator.lastItem::class == item.screen::class
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navigator.replaceAll(item.screen)
                    }
                }
            )
        }
    }
}