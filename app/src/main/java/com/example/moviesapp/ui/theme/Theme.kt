package com.example.moviesapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MoviePrimary,
    primaryContainer = MoviePrimaryContainer,
    secondary = MovieSecondary,
    secondaryContainer = MovieSecondaryContainer,
    background = MovieDarkBackground,
    surface = MovieDarkSurface,
    error = MovieError,
    onPrimary = MovieOnPrimary,
    onSecondary = MovieOnSecondary,
    onBackground = MovieOnPrimary,
    onSurface = MovieOnPrimary,
    onError = MovieOnError,
    onPrimaryContainer = MovieOnPrimary,
    onSecondaryContainer = MovieOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = MoviePrimary,
    primaryContainer = MoviePrimaryContainer,
    secondary = MovieSecondary,
    secondaryContainer = MovieSecondaryContainer,
    background = MovieBackground,
    surface = MovieSurface,
    error = MovieError,
    onPrimary = MovieOnPrimary,
    onSecondary = MovieOnSecondary,
    onBackground = MovieOnBackground,
    onSurface = MovieOnSurface,
    onError = MovieOnError,
    onPrimaryContainer = MovieOnBackground,
    onSecondaryContainer = MovieOnBackground
)

@Composable
fun MoviesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}