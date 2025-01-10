package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moviesapp.data.entity.MovieCart
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CartItem(
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