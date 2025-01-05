package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesapp.R

@Composable
fun CustomBottomAppBar(navController: NavController,currentPageIndex: Int){
    val selectedItem = remember { mutableIntStateOf(currentPageIndex) }
    NavigationBar (
        modifier = Modifier.height(65.dp)
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.mainscreenbottomicon),
                    contentDescription = "Home",
                    modifier = Modifier.size(28.dp)
                )
            },
            selected = selectedItem.intValue == 0,
            onClick = { selectedItem.intValue = 0 },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_list_alt_24),
                    contentDescription = "List",
                    modifier = Modifier.size(28.dp)
                )
            },
            selected = selectedItem.intValue == 1,
            onClick = {selectedItem.intValue = 1 },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.cartscreenbottomicon),
                    contentDescription = "Cart",
                    modifier = Modifier.size(28.dp)
                )
            },
            selected = selectedItem.intValue == 2,
            onClick = { selectedItem.intValue = 2},
        )
    }
}