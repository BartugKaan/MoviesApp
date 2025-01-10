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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapp.data.entity.MovieCart
import com.example.moviesapp.datastore.AppPref
import com.example.moviesapp.ui.components.CartItem
import com.example.moviesapp.ui.components.LottieAnimation
import com.example.moviesapp.ui.viewmodel.CartScreenViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CartScreen(
    paddingValues: PaddingValues,
    cartScreenViewModel: CartScreenViewModel
) {
    val context = LocalContext.current
    val ap = AppPref(context)
    val cartItems = cartScreenViewModel.cartMovieList.observeAsState(initial = emptyList())
    val totalAmount = cartScreenViewModel.totalAmount.observeAsState(initial = 0)
    
    LaunchedEffect(key1 = true) {
        val userName = ap.getUserName()
        cartScreenViewModel.setUserName(userName)
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
            if (cartItems.value.isEmpty()){
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation("https://lottie.host/dfafec30-2889-4fbf-b33e-2b59b0cec5c8/IYN7ROZzrM.lottie")
                        Text("Your cart empty!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
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
                    Text("Clear Cart", color = Color.White)
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

