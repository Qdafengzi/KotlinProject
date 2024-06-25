package ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform


@Composable
fun MainCotent(viewModel: HomeListViewModel = viewModel { HomeListViewModel() },) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(Unit){
        viewModel.initData()
    }

    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 2.dp), ) {
        uiState.forEach {
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
                            println("Message $it")
                        }
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                ) {
                    Image(
                        painterResource(Res.drawable.compose_multiplatform),
                        null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )

                    Text(
                        text = it.title,
                        color = Color(0xFF333333),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                    )
                    Text(
                        text = it.content,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    )
                }
            }
        }
    }
}