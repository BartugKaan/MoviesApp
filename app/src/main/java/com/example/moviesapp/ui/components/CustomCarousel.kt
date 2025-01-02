package com.example.moviesapp.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.moviesapp.data.entity.Movie
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomCarousel(movieList: List<Movie>) {
    if (movieList.size >= 5) {
        val baseUrl = "http://kasimadalan.pe.hu/movies/images/"
        val topRatedMovies = movieList.sortedByDescending { it.rating }.take(5)
        val movieImages = topRatedMovies.map { baseUrl + it.image }

        val repeatedImages = movieImages + movieImages + movieImages
        
        val pagerState = rememberPagerState(
            initialPage = movieImages.size,
            pageCount = { repeatedImages.size }
        )

        LaunchedEffect(pagerState.currentPage) {
            val currentPage = pagerState.currentPage
            
            if (currentPage < movieImages.size) {
                pagerState.scrollToPage(currentPage + movieImages.size)
            }
            else if (currentPage >= movieImages.size * 2) {
                pagerState.scrollToPage(currentPage - movieImages.size)
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 85.dp),
                pageSpacing = (-45).dp
            ) { index ->
                val distanceFromCenter = (pagerState.currentPage - index) + 
                                       pagerState.currentPageOffsetFraction
                
                val scale = 0.8f + (1f - 0.8f) * (1f - abs(distanceFromCenter))
                
                Box(
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = scale
                        rotationZ = -distanceFromCenter * 8f
                    },
                    contentAlignment = Alignment.Center
                ) {
                    PagerCardContent(
                        index = index % movieImages.size,
                        pagerState = pagerState,
                        imageList = movieImages,
                        isSelected = pagerState.currentPage == index
                    )
                }
            }
        }
    }
}