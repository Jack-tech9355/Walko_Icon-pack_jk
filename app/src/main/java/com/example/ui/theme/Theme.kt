package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WalkoDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    primaryContainer = ElectricVioletDark,
    onPrimaryContainer = NeonCyanLight,
    secondary = ElectricViolet,
    onSecondary = Color.White,
    secondaryContainer = MidnightSurface,
    onSecondaryContainer = Color.White,
    tertiary = CyberPink,
    onTertiary = Color.White,
    background = AmoledBlack,
    onBackground = TextPrimary,
    surface = DeepObsidian,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    outlineVariant = BorderSubtle.copy(alpha = 0.5f)
)

@Composable
fun WalkoIconsTheme(
    darkTheme: Boolean = true, // Default to vibrant AMOLED dark mode
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = WalkoDarkColorScheme,
        typography = Typography,
        content = content
    )
}
