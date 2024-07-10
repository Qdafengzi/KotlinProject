package ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kmpapp.Res
import org.example.kmpapp.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import ui.theme.OpenSans
import ui.theme.youYuanFamily
import kotlin.random.Random


data class ProfileItem(
    val title: String,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen() {
    val profileItemList = remember { mutableStateOf<List<ProfileItem>>(mutableListOf()) }
    LaunchedEffect(Unit) {
        val list = mutableListOf<ProfileItem>()
        list.add(ProfileItem("非 WIFI 环境下载"))
        list.add(ProfileItem("默认开启4K 视频"))
        list.add(ProfileItem("默认开启最大分辨率图片"))
        list.add(ProfileItem("开启360dpi"))
        list.add(ProfileItem("手动对焦"))
        profileItemList.value = list
    }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {

            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1f)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .border(width = 1.dp, color = Color.Gray.copy(alpha = 0.6f), shape = CircleShape)
                    )
                }
            }

            item {
                Text(
                    text = "设置",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
                )
            }


            profileItemList.value.forEachIndexed { _, profileItem ->
                item {
                    Item(profileItem.title)
                }
            }
        }
    }
}

@Composable
fun Item(title: String) {
    var checked by remember { mutableStateOf((0..1).random() != 0) }
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                fontSize = 16.sp,
                fontFamily = OpenSans(),
            )
            Switch(checked = checked, onCheckedChange = {
                checked = !checked
            })
        }
        HorizontalDivider()
    }
}
