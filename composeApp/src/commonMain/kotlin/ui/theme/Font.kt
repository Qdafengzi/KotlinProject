package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.example.kmpapp.OpenSans_Bold
import org.example.kmpapp.OpenSans_ExtraBold
import org.example.kmpapp.OpenSans_Light
import org.example.kmpapp.OpenSans_Medium
import org.example.kmpapp.OpenSans_Regular
import org.example.kmpapp.OpenSans_SemiBold
import org.example.kmpapp.Res
import org.example.kmpapp.you_yuan
import org.jetbrains.compose.resources.Font


@Composable
fun youYuanFamily() = FontFamily(
    Font(resource = Res.font.you_yuan, weight = FontWeight.SemiBold, style = FontStyle.Italic),
)

@Composable
fun OpenSans() = FontFamily(
    Font(
        resource = Res.font.OpenSans_Regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(resource = Res.font.OpenSans_Light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(resource = Res.font.OpenSans_Medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resource = Res.font.OpenSans_Bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(
        resource = Res.font.OpenSans_SemiBold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resource = Res.font.OpenSans_ExtraBold,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
)