package com.example.moviesapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesapp.R
import com.example.moviesapp.data.entity.OnboardingCardData
import com.example.moviesapp.ui.components.OnboardingCardContent
import com.example.moviesapp.ui.components.OnboardingCardWithTf
import com.example.moviesapp.ui.theme.MoviesAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()
    
    val onboardingCards = listOf(
        OnboardingCardData(
            imageResource = R.drawable.onboarding1,
            title = "Discover Your Favorite Movies",
            description = "Browse through a wide selection of films and find your next favorite to watch or own."
        ),
        OnboardingCardData(
            imageResource = R.drawable.onboarding2,
            title = "Create Your Wishlist",
            description = "Save the movies you love to your wishlist and access them anytime with ease."
        ),
        OnboardingCardData(
            imageResource = R.drawable.onboarding3,
            title = "Manage Your Profile",
            description = "Track your purchases, update your preferences, and make the most of your movie experience."
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            if (page < 3) {
                val onboardingCard = onboardingCards[page]
                OnboardingCardContent(
                    imageResource = onboardingCard.imageResource,
                    title = onboardingCard.title,
                    description = onboardingCard.description
                )
            } else {
                OnboardingCardWithTf(navController)
            }
        }

        // Dots indicator
        Row(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(10.dp)
                        .background(color = color, shape = CircleShape)
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            enabled = pagerState.currentPage < 3
        ) {
            Text(text = "Next")
        }
    }
}

