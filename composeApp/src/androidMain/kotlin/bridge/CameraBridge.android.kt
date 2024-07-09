package bridge

import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun CameraView(
    zoomFactor: Float,
    onZoomFactorChange: (Float) -> Unit
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = PreviewView.ScaleType.FIT_CENTER
        }
    }

    val camera = remember { mutableStateOf<Camera?>(null) }

    AndroidView(
        factory = {
            val cameraProvider = cameraProviderFuture.get()
            val resolutionStrategy = ResolutionSelector.Builder().setResolutionStrategy(
                ResolutionStrategy(
                    android.util.Size(1080, 1080),
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
                )
            ).build()

            val preview = Preview.Builder()
                .setResolutionSelector(resolutionStrategy)
                .build()
            preview.surfaceProvider = previewView.surfaceProvider
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
            camera.value = cameraProvider.bindToLifecycle(lifeCycleOwner, cameraSelector, preview)
            previewView
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        update = {
            camera.value?.apply {
                // 更新相机的缩放级别
                val cameraControl = this.cameraControl
                cameraControl.setLinearZoom(zoomFactor)
                // 监听缩放级别的变化
                this.cameraInfo.zoomState.observe(lifeCycleOwner) { zoomState ->
                    onZoomFactorChange(zoomState.linearZoom)
                }
            }
        })
}