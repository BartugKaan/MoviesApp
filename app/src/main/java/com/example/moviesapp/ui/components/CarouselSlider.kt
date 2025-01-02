package com.example.moviesapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.moviesapp.data.entity.Movie
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun CarouselSlider(movies: List<Movie>) {
    var currentImageIndex by remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val ratingBasedSortedMovies = movies.sortedByDescending { it.rating }
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"
    val bestFiveMovieImage = listOf(
        baseUrl + ratingBasedSortedMovies[0].image,
        baseUrl + ratingBasedSortedMovies[1].image,
        baseUrl + ratingBasedSortedMovies[2].image,
        baseUrl + ratingBasedSortedMovies[3].image,
        baseUrl + ratingBasedSortedMovies[4].image,
    )

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            while (true) {
                delay(5000)
                if (currentImageIndex == bestFiveMovieImage.size - 1) currentImageIndex = 0
                else currentImageIndex++
                scrollState.animateScrollToItem(currentImageIndex)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            LazyRow(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(bestFiveMovieImage) { index, image ->
                    Card(
                        modifier = Modifier
                            .height(300.dp)
                            .padding(10.dp)
                            .graphicsLayer {
                                scaleX = if (currentImageIndex == index) 1.1f else 1f
                                scaleY = if (currentImageIndex == index) 1.1f else 1f
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        GlideImage(
                            imageModel = image,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(200.dp)
                        )
                    }
                }
            }
        }
    }
}