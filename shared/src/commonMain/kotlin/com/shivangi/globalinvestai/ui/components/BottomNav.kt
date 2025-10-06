package com.shivangi.globalinvestai.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.shivangi.globalinvestai.ui.screens.AiAssistantScreen
import com.shivangi.globalinvestai.ui.screens.DiscoverScreen
import com.shivangi.globalinvestai.ui.screens.HomeScreen
import com.shivangi.globalinvestai.ui.screens.PortfolioScreen
import com.shivangi.globalinvestai.ui.screens.ProfileScreen
import cafe.adriel.voyager.navigator.Navigator
import com.shivangi.globalinvestai.ui.theme.Primary

@Composable
fun BottomNav(navigator: Navigator) {

    val navItems = listOf(
        BottomNavItem("Home", HomeIcon, HomeScreen),
        BottomNavItem("Discover", SearchIcon, DiscoverScreen),
        BottomNavItem("Assistant", SparklesIcon, AiAssistantScreen),
        BottomNavItem("Portfolio", PortfolioIcon, PortfolioScreen),
        BottomNavItem("Profile", UserIcon, ProfileScreen)
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Primary
    ) {
        navItems.forEach { item ->
            val isSelected = navigator.lastItem::class == item.screen::class
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp, // Reduced font size
                        maxLines = 1 // Ensures text stays on a single line
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navigator.replaceAll(item.screen)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}