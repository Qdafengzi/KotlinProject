package ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bridge.CameraView


@Composable
fun CameraScreen() {
    var zoomFactor by remember { mutableStateOf(0.5f) }  // 初始化缩放级别在中间

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        CameraView(
            zoomFactor = zoomFactor,
            onZoomFactorChange = { newZoomFactor ->
                zoomFactor = newZoomFactor
            }
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Zoom: ${(zoomFactor * 100).toInt()}%")

            Slider(
                value = zoomFactor,
                onValueChange = { newZoomFactor ->
                    zoomFactor = newZoomFactor
                },
                steps = 100,
                valueRange = 0f..1f,
                modifier = Modifier,
                colors = SliderDefaults.colors(
                ),

            )
        }

    }
}

