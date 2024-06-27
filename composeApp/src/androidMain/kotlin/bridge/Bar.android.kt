package bridge

import android.view.WindowInsets
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import org.example.kmpapp.MainActivity


@Composable
actual fun PlatformColors(statusBarColor: Color, navBarColor: Color) {
    val context = LocalContext.current as MainActivity
    context.apply {
//        enableEdgeToEdge()

//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
//            navigationBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb())
//        )

//        if (isSystemInDarkTheme()) {
//            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.light(
//                    Color.White.toArgb(), statusBarColor.toArgb(),
//                ),
//                navigationBarStyle = SystemBarStyle.light(
//                    Color.Black.toArgb(), navBarColor.toArgb(),
//                ),
//            )
//        } else {
//            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.dark(
//                    statusBarColor.toArgb(),
//                ),
//                navigationBarStyle = SystemBarStyle.dark(
//                    navBarColor.toArgb(),
//                ),
//            )
//        }
    }


}




