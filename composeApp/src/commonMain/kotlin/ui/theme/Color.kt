package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF006B58)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFFFFF)

val md_theme_dark_primary = Color(0xFF5DDBBC)
val md_theme_dark_onPrimary = Color(0xFF00382D)
val md_theme_dark_primaryContainer = Color(0xFF005142)

val seed = Color(0xFF006B58)

val freshColor = Color(0xFF44FF78)
val staleColor = Color(0xFFFF9E44)

val primaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF86A8FC)
    else Color(0xFF283556)

val headerColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF0C0C0C)
    else Color(0xFF283556)

val surfaceColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF161616)
    else Color(0xFFFFFFFF)

val textColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF000000)