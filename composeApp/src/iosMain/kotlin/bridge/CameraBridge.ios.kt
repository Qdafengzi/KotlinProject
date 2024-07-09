package bridge

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.position
import platform.AVFoundation.videoZoomFactor
import platform.CoreGraphics.CGRect
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)
@Composable
actual fun CameraView(
    zoomFactor: Float,
    onZoomFactorChange: (Float) -> Unit
) {
    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
        (device as AVCaptureDevice).position == AVCaptureDevicePositionBack
    }!! as AVCaptureDevice

    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) as AVCaptureDeviceInput

    val output = AVCaptureStillImageOutput()
    output.outputSettings = mapOf(AVVideoCodecKey to AVVideoCodecJPEG)

    val session = AVCaptureSession()

    session.sessionPreset = AVCaptureSessionPresetPhoto

    session.addInput(input)
    session.addOutput(output)

    val cameraPreviewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }

    UIKitView(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1f),
        background = Color.Black,
        factory = {
            val container = UIView()
            container.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            session.startRunning()
            container
        },
        onResize = { container: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            container.layer.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        },
        update = {
            // Update the zoom factor
            try {
                device.lockForConfiguration(null)
                device.videoZoomFactor =
                    zoomFactor * (device.activeFormat.videoMaxZoomFactor - 1.0f) + 1.0f
                device.unlockForConfiguration()
            } catch (e: Exception) {
                // Handle any exceptions
                println("Error setting zoom factor: ${e.message}")
            }
        }
    )
}
