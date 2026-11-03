package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkRedPrimary,
    onPrimary = Color.White,
    primaryContainer = BrandRedDark,
    onPrimaryContainer = Color.White,
    secondary = SaffronGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF422B00),
    onSecondaryContainer = Color(0xFFFFD54F),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    error = Color(0xFFCF6679)
)

private val LightColorScheme = lightColorScheme(
    primary = BrandRedPrimary,
    onPrimary = Color.White,
    primaryContainer = BrandRedLight,
    onPrimaryContainer = BrandRedDark,
    secondary = SaffronGold,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF3E0),
    onSecondaryContainer = AmberDark,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    error = Color(0xFFB00020)
)

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

@Composable
fun FirstIndiaTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
