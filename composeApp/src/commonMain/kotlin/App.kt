import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import bridge.PlatformColors
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.kmpapp.Res
import org.example.kmpapp.ic_collection
import org.example.kmpapp.ic_download
import org.example.kmpapp.ic_share
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.bottom.BottomContent
import ui.bottom.NavHostContainer
import ui.theme.MultiplatformTheme
import utils.XLogger
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(homeViewModel: HomeListViewModel = androidx.lifecycle.viewmodel.compose.viewModel { HomeListViewModel() }) {
    MultiplatformTheme {
        val uiState = homeViewModel.uiState.collectAsState().value
        PlatformColors(Color.White, Color.White)
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        val bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = false
        )
        val sheetState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)


        LaunchedEffect(uiState.homeDataList) {
            XLogger.d("homeList change")
            if (uiState.homeDataList.isEmpty()){
                return@LaunchedEffect
            }

            if (uiState.homeDataList.size < uiState.currentPage) {
                return@LaunchedEffect
            }

            val count = uiState.homeDataList[uiState.currentPage].list.count {
                it.selected
            }
            XLogger.d("------>${count}")
            if (count > 0) {
                bottomSheetState.expand()
            } else {
                bottomSheetState.hide()
            }
        }

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
            }) {

            BottomSheetScaffold(
                scaffoldState = sheetState,
                modifier = Modifier.fillMaxSize()
                    .systemBarsPadding(),
                sheetPeekHeight = 0.dp,
                sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                sheetTonalElevation = 10.dp,
                sheetShadowElevation = 5.dp,
                sheetSwipeEnabled = true,
                sheetContainerColor = Color.White,
                sheetContent = {
                    BottomSheet()
                }
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .systemBarsPadding(),
                    bottomBar = {
                        BottomContent(navController)
                    }
                ) { innerPadding ->
                    NavHostContainer(navController, homeViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun BottomSheet() {
    val toaster = rememberToasterState()
    Toaster(state = toaster, maxVisibleToasts = 1)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    toaster.show("分享成功", type = ToastType.Info, duration = 1000.milliseconds)
                }
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(Res.drawable.ic_share),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                "分享",
            )
        }

        Column(
            modifier = Modifier
                .clickable {
                    toaster.show("下载成功", type = ToastType.Info, duration = 1000.milliseconds)
                }
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(Res.drawable.ic_download),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                "下载",
            )
        }

        Column(
            modifier = Modifier
                .clickable {
                    toaster.show("收藏成功", type = ToastType.Warning, duration = 1000.milliseconds)
                }
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(Res.drawable.ic_collection),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                "收藏",
            )
        }
    }
}