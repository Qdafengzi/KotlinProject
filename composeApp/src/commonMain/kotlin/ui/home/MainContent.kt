package ui.home

import HomeListViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import entity.HomeTabsEntity
import ext.dpToPx
import ext.pxToDp
import org.jetbrains.compose.resources.painterResource

import kotlinx.coroutines.launch
import org.example.kmpapp.Res
import org.example.kmpapp.compose_multiplatform
import org.example.kmpapp.ic_select_default
import org.example.kmpapp.ic_selected
import ui.theme.youYuanFamily
import utils.XLogger
import kotlin.math.abs
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(viewModel: HomeListViewModel) {
    val homeTabList = viewModel.uiState.collectAsState().value.homeTabs

    LaunchedEffect(Unit) {
        viewModel.initData()
    }

    val toolbarHeight = 48.dp
    val toolbarHeightPx = 48.dp.dpToPx()
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = derivedStateOf {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                XLogger.d("delta:${delta}")
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val state = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = state,
        sheetBackgroundColor = Color.Blue,
        sheetElevation = 10.dp,
        sheetGesturesEnabled = true,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.height(400.dp)
            ) {
                Text("sheetContent")
            }
        }) {

        LaunchedEffect(UInt) {
            viewModel.initHomeTabList()
        }

        Column(
            modifier = Modifier
                .nestedScroll(nestedScrollConnection.value)
        ) {
            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth()
                    .height(toolbarHeight - abs(toolbarOffsetHeightPx.floatValue.roundToInt()).pxToDp())
                ,
                selectedTabIndex = 0,
                backgroundColor = Color.White,
                contentColor = Color.Black,
                edgePadding = 0.dp,
                indicator = {
                    //Box(modifier = Modifier.height(2.dp).background(color = Color.Blue, shape = RoundedCornerShape(1.dp)))
                },
                divider = {
//                    HorizontalDivider()
                },
                tabs = {
                    homeTabList.forEachIndexed { index, homeTabsEntity ->
                        Tab(
                            modifier = Modifier.fillMaxWidth(),
                            unselectedContentColor = Color.White,
                            selectedContentColor = Color.Black,
                            selected = homeTabsEntity.selected,
                            onClick = {
                                viewModel.updateTabClick(index)
                            }, content = {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(
                                                topEndPercent = 50,
                                                topStartPercent = 50,
                                                bottomEndPercent = 50,
                                                bottomStartPercent = 50
                                            )
                                        )
                                        .background(color = if (homeTabsEntity.selected) Color.Blue.copy(alpha = 0.5f) else Color.Gray)
                                        .padding(horizontal = 16.dp, vertical = 12.dp),

                                    contentAlignment = Alignment.Center,
                                ){
                                    Text(
                                        text = homeTabsEntity.title,
                                        fontWeight = if (homeTabsEntity.selected) FontWeight.Medium else FontWeight.Normal,
                                        )
                                }
                            }
                        )
                    }
                }
            )
            ListView(viewModel)
        }
    }
}

@Composable
fun ListView(viewModel: HomeListViewModel) {
    val homeList = viewModel.uiState.collectAsState().value.homeList
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 2.dp),
    ) {
        homeList.forEachIndexed { index, homeListEntity ->
            item {
                Column(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 4.dp,
                        )
                        .fillMaxWidth()
                        .aspectRatio(4 / 6f)
                        .clickable {
                            viewModel.changeSelectedStatus(index)
                        }
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        Image(
                            painterResource(Res.drawable.compose_multiplatform),
                            null,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        Image(
                            painterResource(if (homeListEntity.selected) Res.drawable.ic_selected else Res.drawable.ic_select_default),
                            null,
                            modifier = Modifier
                                .padding(start = 2.dp, top = 2.dp)
                                .align(Alignment.TopStart)
                                .size(16.dp)
                        )
                    }

                    Text(
                        text = homeListEntity.title,
                        color = Color(0xFF333333),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                    )
                    Text(
                        text = homeListEntity.content,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    )
                }
            }
        }
    }
}