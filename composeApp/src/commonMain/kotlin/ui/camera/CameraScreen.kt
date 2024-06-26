package ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import bridge.CameraView


@Composable
fun CameraScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        CameraView()
    }
}

