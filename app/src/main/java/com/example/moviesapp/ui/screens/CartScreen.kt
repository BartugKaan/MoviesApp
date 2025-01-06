package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
    val cartMovieList = cartScreenViewModel.cartMovieList.observeAsState(listOf())
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(cartMovieList.value) { item ->
                CartItem(
                    item = item,
                    baseUrl = baseUrl,
                    onIncrease = {  }, // TODO: Increase orderAmount
                    onDecrease = {  }, // TODO: Decrease orderAmount
                    onRemove = { cartScreenViewModel.deleteMovieFromCart(item.cartId, "BartugKaan") }
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

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
                Text(
                    text = "Toplam Tutar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₺${cartMovieList.value.sumOf { it.price * it.orderAmount }}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

//TODO: Custom CarItem will be added
@Composable
private fun CartItem(
    item: MovieCart,
    baseUrl: String,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = baseUrl + item.image,
                modifier = Modifier.size(80.dp, 120.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "₺${item.price}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDecrease) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Decrease quantity"
                        )
                    }
                    Text(
                        text = "${item.orderAmount}",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 16.sp
                    )
                    IconButton(onClick = onIncrease) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Increase quantity"
                        )
                    }
                }
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