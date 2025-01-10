package com.example.moviesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.ui.screens.MainScreen
import com.example.moviesapp.ui.screens.MovieDetail
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import com.example.moviesapp.ui.viewmodel.MovieDetailScreenViewModel
import com.google.gson.Gson
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.example.moviesapp.ui.components.CustomBottomAppBar
import com.example.moviesapp.ui.screens.CartScreen
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.moviesapp.datastore.AppPref
import com.example.moviesapp.ui.screens.CategoryScreen
import com.example.moviesapp.ui.screens.OnboardingScreen
import com.example.moviesapp.ui.screens.ProfileScreen
import com.example.moviesapp.ui.viewmodel.CartScreenViewModel
import com.example.moviesapp.ui.viewmodel.CategoryScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    title: String = "Popcorn Deals",
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Popcorn Deals",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = { 
            CustomBottomAppBar(
                navController = LocalNavController.current,
                currentPageIndex = when(LocalNavController.current.currentDestination?.route) {
                    "mainScreen" -> 0
                    "movieDetailScreen/{movie}" -> 0
                    "cartScreen" -> 2
                    "profileScreen" -> 3
                    else -> 0
                }
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun ScreenNavigation(
    mainScreenViewModel: MainScreenViewModel,
    movieDetailScreenViewModel: MovieDetailScreenViewModel,
    cartScreenViewModel: CartScreenViewModel,
    categoryScreenViewModel: CategoryScreenViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val ap = AppPref(context)
    val isOnboardingSeen = remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        CoroutineScope(Dispatchers.Main).launch {
            isOnboardingSeen.value = ap.getOnboardingPreferences()
        }
    }

    CompositionLocalProvider(LocalNavController provides navController) {
        var startDestination = ""
        if (isOnboardingSeen.value){
            startDestination = "mainScreen"
        }
        else{
            startDestination = "onboardingScreen"
        }


        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = "onboardingScreen"){
                OnboardingScreen(navController)
            }
            composable(route = "mainScreen") {
                AppScreen(title = "Popcorn Deals") { paddingValues ->
                    MainScreen(
                        mainScreenViewModel = mainScreenViewModel,
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
            composable(
                route = "movieDetailScreen/{movie}",
                arguments = listOf(navArgument("movie") { type = NavType.StringType })
            ) {
                val movieJson = it.arguments?.getString("movie")
                val movieObject = Gson().fromJson(movieJson, Movie::class.java)
                AppScreen(title = movieObject.name) { paddingValues ->
                    MovieDetail(movie = movieObject, movieDetailScreenViewModel = movieDetailScreenViewModel, navController = navController)
                }
            }
            composable(
                route = "categoryScreen/{category}",
                arguments = listOf(navArgument("category"){type = NavType.StringType})
            ){
                val categoryName = it.arguments?.getString("category")
                if (categoryName != null) {
                    AppScreen(title = categoryName ) { paddingValues ->
                        CategoryScreen(
                            categoryScreenViewModel = categoryScreenViewModel,
                            navController = navController,
                            paddingValues = paddingValues,
                            categoryName = categoryName
                        )
                    }
                }
            }
            composable(route = "cartScreen") {
                AppScreen(title = "Cart") { paddingValues ->
                    CartScreen(cartScreenViewModel = cartScreenViewModel,paddingValues = paddingValues)
                }
            }
            composable(route = "profileScreen"){
                AppScreen(title = "Profile") {
                    ProfileScreen(navController = navController)
                }
            }

        }
    }
}

private val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}