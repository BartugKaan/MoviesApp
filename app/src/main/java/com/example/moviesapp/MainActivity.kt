package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.ui.navigation.ScreenNavigation
import com.example.moviesapp.ui.screens.MainScreen
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.ui.viewmodel.CartScreenViewModel
import com.example.moviesapp.ui.viewmodel.CategoryScreenViewModel
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import com.example.moviesapp.ui.viewmodel.MovieDetailScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    private val  movieDetailScreenViewModel: MovieDetailScreenViewModel by viewModels()
    private val cartScreenViewModel: CartScreenViewModel by viewModels()
    private val  categoryScreenViewModel: CategoryScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                ScreenNavigation(
                    mainScreenViewModel = mainScreenViewModel,
                    movieDetailScreenViewModel = movieDetailScreenViewModel,
                    cartScreenViewModel = cartScreenViewModel,
                    categoryScreenViewModel = categoryScreenViewModel
                )
            }
        }
    }
}