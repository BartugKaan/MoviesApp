package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomAppBar(navController: NavController, currentPageIndex: Int) {
    NavigationBar(
        modifier = Modifier.height(65.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        NavigationBarItem(
            icon = { 
                Icon(
                    Icons.Default.Home, 
                    contentDescription = "Home",
                    tint = if (currentPageIndex == 0) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                ) 
            },
            selected = currentPageIndex == 0,
            onClick = { navController.navigate("mainScreen") }
        )
        NavigationBarItem(
            icon = { 
                Icon(
                    Icons.Default.Favorite, 
                    contentDescription = "Favorites",
                    tint = if (currentPageIndex == 1) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                ) 
            },
            selected = currentPageIndex == 1,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { 
                Icon(
                    Icons.Default.ShoppingCart, 
                    contentDescription = "Cart",
                    tint = if (currentPageIndex == 2) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                ) 
            },
            selected = currentPageIndex == 2,
            onClick = { navController.navigate("cartScreen") }
        )
        NavigationBarItem(
            icon = { 
                Icon(
                    Icons.Default.Person, 
                    contentDescription = "Profile",
                    tint = if (currentPageIndex == 3) 
                        MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                ) 
            },
            selected = currentPageIndex == 3,
            onClick = { navController.navigate("profileScreen") }
        )
    }
}