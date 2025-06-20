package com.example.mwakdasicollection.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

// Data class representing each navigation item
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,      // List of navigation items
    selectedIndex: Int,             // Currently selected index
    onItemSelected: (Int) -> Unit   // Callback when an item is selected
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

// Example of how to use BottomNavigationBar with predefined navigation items
@Composable
fun AppBottomNavigation(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(label = "Home", icon = Icons.Default.Home, route = "home"),
        BottomNavItem(label = "Profile", icon = Icons.Default.Person, route = "profile"),
        BottomNavItem(label = "Settings", icon = Icons.Default.Settings, route = "settings")
    )
    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }

    BottomNavigationBar(
        items = items,
        selectedIndex = if (selectedIndex >= 0) selectedIndex else 0,
        onItemSelected = { index ->
            onNavigate(items[index].route)
        }
    )
}