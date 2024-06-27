import androidx.compose.foundation.layout.*
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
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
        PlatformColors(Color.White, Color.White)
        val navController = rememberNavController()

        ModalDrawer(
            modifier = Modifier.fillMaxSize().systemBarsPadding(),
            drawerContent = {
                Box() {
                    Text("Draw")

                }
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
                    .systemBarsPadding(),
                bottomBar = {
                    BottomContent(navController)
                }
            ) { innerPadding ->
                NavHostContainer(navController, Modifier.padding(innerPadding))
            }
        }


    }
}