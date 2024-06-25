package ui.home

import HomeListViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.ic_select_default
import kotlinproject.composeapp.generated.resources.ic_selected


@Composable
fun MainCotent(viewModel: HomeListViewModel = viewModel { HomeListViewModel() }) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.initData()
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 2.dp),
    ) {
        uiState.forEachIndexed { index, homeListEntity ->
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
                   Box(  modifier = Modifier
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
                           painterResource(if(homeListEntity.selected) Res.drawable.ic_selected else Res.drawable.ic_select_default),
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