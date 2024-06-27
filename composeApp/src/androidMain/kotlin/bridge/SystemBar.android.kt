package bridge

import android.app.Activity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import org.example.kmpapp.MainActivity


@Composable
actual fun PlatformColors(statusBarColor: Color, navBarColor: Color) {
    val context = LocalContext.current as MainActivity
    val view  =  LocalView.current
    context.apply {
//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(
//                statusBarColor.toArgb(),
//                statusBarColor.toArgb()
//            ),
//            navigationBarStyle = SystemBarStyle.auto(
//                navBarColor.toArgb(),
//                navBarColor.toArgb()
//            )
//        )
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navBarColor.toArgb()
        if (!view.isInEditMode) {
            val isDark = isSystemInDarkTheme()
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = statusBarColor.toArgb()
                window.navigationBarColor = navBarColor.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
            }
        }
    }
}




