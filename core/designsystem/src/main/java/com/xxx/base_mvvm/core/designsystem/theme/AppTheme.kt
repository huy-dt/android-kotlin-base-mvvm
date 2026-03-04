package com.xxx.base_mvvm.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary       = Primary,
    secondary     = Secondary,
    background    = Background,
    surface       = Surface,
    error         = Error,
    onPrimary     = OnPrimary,
    onSecondary   = OnSecondary,
    onBackground  = OnBackground,
    onSurface     = OnSurface,
)

private val DarkColors = darkColorScheme(
    primary      = PrimaryDark,
    background   = BackgroundDark,
    surface      = SurfaceDark,
    onBackground = OnBackgroundDark,
    onSurface    = OnSurfaceDark,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = AppTypography,
        content     = content
    )
}
