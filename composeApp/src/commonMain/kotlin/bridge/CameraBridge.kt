package bridge

import androidx.compose.runtime.Composable


@Composable
expect fun CameraView(
    zoomFactor: Float,
    onZoomFactorChange: (Float) -> Unit
)

