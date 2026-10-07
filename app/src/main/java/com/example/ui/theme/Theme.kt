package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WalkoCreamColorScheme = lightColorScheme(
    primary = WarmCaramel,
    onPrimary = Color.White,
    primaryContainer = WarmCaramelLight,
    onPrimaryContainer = WarmCaramelDark,
    secondary = WarmAmber,
    onSecondary = Color.White,
    secondaryContainer = CreamSurfaceVariant,
    onSecondaryContainer = TextEspresso,
    tertiary = WarmCinnamon,
    onTertiary = Color.White,
    background = CreamCanvas,
    onBackground = TextEspresso,
    surface = CreamSurface,
    onSurface = TextEspresso,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = TextCocoa,
    outline = CreamBorder,
    outlineVariant = CreamBorder.copy(alpha = 0.5f)
)

@Composable
fun WalkoIconsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = WalkoCreamColorScheme,
        typography = Typography,
        content = content
    )
}
