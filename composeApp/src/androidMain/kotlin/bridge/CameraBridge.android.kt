package bridge

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun CameraView() {
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

    AndroidView(
        factory = {
            previewView
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        update = {
            val cameraProvider = cameraProviderFuture.get()
            val resolutionStrategy = ResolutionSelector.Builder().setResolutionStrategy(
                ResolutionStrategy(
                    android.util.Size(1080, 1080),
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
                )
            ).build()

            val preview = Preview.Builder()
//                .setTargetResolution(screenSize)
                .setResolutionSelector(resolutionStrategy)
                .setPreviewStabilizationEnabled(true)
//                .setCustomOrderedResolutions(listOf(android.util.Size(1080,1080)))
                .build()
            preview.surfaceProvider = previewView.surfaceProvider
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
            cameraProvider.bindToLifecycle(lifeCycleOwner, cameraSelector, preview)
        })
}