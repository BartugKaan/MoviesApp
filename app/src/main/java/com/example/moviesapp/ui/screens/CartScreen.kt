package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapp.data.entity.MovieCart
import com.example.moviesapp.ui.viewmodel.CartScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CartScreen(
    paddingValues: PaddingValues,
    cartScreenViewModel: CartScreenViewModel
) {
    val cartItems = cartScreenViewModel.cartMovieList.observeAsState(initial = emptyList())
    val totalAmount = cartScreenViewModel.totalAmount.observeAsState(initial = 0)
    
    LaunchedEffect(key1 = true) {
        cartScreenViewModel.loadCart()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Cart Items List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(cartItems.value) { movie ->
                CartItem(
                    movie = movie,
                    onQuantityChange = { newQuantity ->
                        cartScreenViewModel.updateQuantity(movie.cartId, newQuantity)
                    },
                    onRemove = {
                        cartScreenViewModel.removeFromCart(movie.cartId)
                    }
                )
            }
        }
        
        // Total Amount and Clear Cart Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { cartScreenViewModel.clearCart() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Cart")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Total Amount",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₺${totalAmount.value}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun CartItem(
    movie: MovieCart,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Movie Image
            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                modifier = Modifier.size(80.dp)
            )
            
            // Movie Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = movie.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₺${movie.price}",
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = { onQuantityChange(movie.orderAmount - 1) }) {
                    Text("-")
                }
                
                Text(
                    text = movie.orderAmount.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                
                IconButton(onClick = { onQuantityChange(movie.orderAmount + 1) }) {
                    Text("+")
                }
                
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}