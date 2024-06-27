package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.example.kmpapp.Res
import org.example.kmpapp.you_yuan
import org.jetbrains.compose.resources.Font


@Composable
fun youYuanFamily() = FontFamily(
    Font(resource = Res.font.you_yuan, weight = FontWeight.SemiBold, style = FontStyle.Italic),
)