package ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import bridge.CameraView


@Composable
fun CameraScreen() {
    val progress = remember { mutableFloatStateOf(50f) }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        CameraView()

        Slider(
            value = progress.floatValue,
            onValueChange = {
                progress.floatValue = it
            },
            steps = 100,
            valueRange = 1f..100f,
        )
    }
}

