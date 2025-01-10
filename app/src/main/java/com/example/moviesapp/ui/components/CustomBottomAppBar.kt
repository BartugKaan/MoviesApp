package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomAppBar(navController: NavController, currentPageIndex: Int) {
    NavigationBar(
        modifier = Modifier.height(65.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = currentPageIndex == 0,
            onClick = { navController.navigate("mainScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            selected = currentPageIndex == 1,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            selected = currentPageIndex == 2,
            onClick = { navController.navigate("cartScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            selected = currentPageIndex == 3,
            onClick = { navController.navigate("profileScreen") }
        )
    }
}