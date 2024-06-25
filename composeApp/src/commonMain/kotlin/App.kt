import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.bottom.BottomContent
import ui.bottom.NavHostContainer


@Composable
@Preview
fun App() {
    MaterialTheme {
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