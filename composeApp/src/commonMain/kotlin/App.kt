import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import bridge.PlatformColors
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
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
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        ModalDrawer(
            modifier = Modifier.fillMaxSize().systemBarsPadding(),
            drawerState = drawerState,
            drawerBackgroundColor = Color.White,
            drawerElevation = 10.dp,
            drawerContentColor = Color.Blue,
            drawerContent = {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    KamelImage(
                        resource = asyncPainterResource(data = "https://www.gravatar.com/avatar/98a5fa293cb82c5bac8d753a0ce52faf?s=256&d=identicon&r=PG&f=y&so-version=2"),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f)
                    )
                    Text(
                        text = "COMPOSE MULTIPLATFORM",
                        modifier = Modifier.padding(top = 20.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,

                    )
                }
        }){
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