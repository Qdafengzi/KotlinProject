import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import bridge.PlatformColors
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.bottom.BottomContent
import ui.bottom.NavHostContainer
import ui.theme.MultiplatformTheme


@Composable
@Preview
fun App() {
    MultiplatformTheme {
        PlatformColors(Color.Transparent, Color.Transparent)
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomContent(navController)
            }
        ) {innerPadding->
            NavHostContainer(navController, Modifier.padding(innerPadding))
        }
    }
}