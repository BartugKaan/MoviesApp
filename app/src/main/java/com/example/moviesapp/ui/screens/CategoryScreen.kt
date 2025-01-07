package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesapp.ui.components.MovieCard
import com.example.moviesapp.ui.viewmodel.CategoryScreenViewModel

@Composable
fun CategoryScreen(
    categoryScreenViewModel: CategoryScreenViewModel,
    navController: NavController,
    paddingValues: PaddingValues,
    categoryName: String
) {
    val movieList = categoryScreenViewModel.movieList.observeAsState(listOf())

    LaunchedEffect(key1 = true) {
        categoryScreenViewModel.getAllMovies()
    }

    val filteredMovies = when (categoryName) {
        "All" -> movieList.value
        else -> movieList.value.filter { it.category == categoryName }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (movieList.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredMovies) { movie ->
                    MovieCard(
                        movie = movie,
                        navController = navController,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}