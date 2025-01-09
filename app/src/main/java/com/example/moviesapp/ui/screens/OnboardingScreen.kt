package com.example.moviesapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapp.R
import com.example.moviesapp.data.entity.OnboardingCardData
import com.example.moviesapp.ui.components.OnboardingCardContent
import com.example.moviesapp.ui.theme.MoviesAppTheme
import kotlinx.coroutines.launch




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen() {
    val pagerState = rememberPagerState(pageCount = { 3 })
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


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                verticalAlignment = Alignment.CenterVertically,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val onboardingCard = onboardingCards[page]
                OnboardingCardContent(
                    imageResource = onboardingCard.imageResource,
                    title = onboardingCard.title,
                    description = onboardingCard.description,
                )
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < onboardingCards.size - 1) {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Next")
            }
        }
    }
}

@Preview
@Composable
fun PreviewOnboardingScreen() {
    MoviesAppTheme {
        OnboardingScreen()
    }
}
