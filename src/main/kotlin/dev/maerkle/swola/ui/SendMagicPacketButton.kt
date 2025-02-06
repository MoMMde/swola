import android.util.Log
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.maerkle.swola.network.BROADCAST_ADDRESS
import dev.maerkle.swola.network.MAGIC_PACKET_UDP_PORT
import dev.maerkle.swola.network.sendMagicPacket
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun SendMagicPacketButton(macAddress: String, broadcastAddress: String, port: Int, onClick: (Offset) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val haptic = LocalHapticFeedback.current

    var offset = Offset(0f, 0f)
    val buttonSize = 60.dp
    val offsetInPx = LocalDensity.current.run { buttonSize.toPx() }.roundToInt().toFloat() * 0.8f

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)) // Clip the inner Box to the rounded shape
            .size(buttonSize, buttonSize)
            .background(Color.Transparent)
            .onGloballyPositioned { layoutCoordinates ->
                offset = layoutCoordinates.positionInParent().plus(Offset(offsetInPx, offsetInPx))
            }
            .clickable(onClick = {
                Log.i("SendMagicPacketButton", "Send Magic Packet to $macAddress")
                onClick(offset)
                haptic.performHapticFeedback(HapticFeedbackType(HapticFeedbackConstants.LONG_PRESS))
                coroutineScope.launch {
                    sendMagicPacket(macAddress, broadcastAddress, port)
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawIntoCanvas { canvas ->
                // Draw the gradient
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFEEB8FF), // Faded pink-magenta
                        Color(0xFFB43DC9).copy(alpha = 1f) // Slightly more transparent
                    )
                )
                drawRect(brush = gradientBrush)

                // Draw the noise
                drawNoise(canvas, size.width, size.height, 0.05f)
            }
        }
        Icon(
            Icons.Outlined.PowerSettingsNew,
            contentDescription = "Send Magic Packet",
            tint = Color.White
        )
    }
}

private fun DrawScope.drawNoise(
    canvas: androidx.compose.ui.graphics.Canvas,
    width: Float,
    height: Float,
    density: Float
) {
    val random = Random(0) // Use a fixed seed for consistent noise
    val noisePoints = (width * height * density).toInt()

    for (i in 0 until noisePoints) {
        val x = random.nextFloat() * width
        val y = random.nextFloat() * height
        val color = Color(
            red = random.nextFloat(),
            green = random.nextFloat(),
            blue = random.nextFloat(),
            alpha = random.nextFloat() * 0.2f // Adjust alpha for noise intensity
        )
        canvas.drawCircle(
            center = Offset(x, y),
            radius = 1f,
            paint = androidx.compose.ui.graphics.Paint().apply {
                this.color = color
            }
        )
    }
}

@Preview
@Composable
fun SendMagicPacketButtonPreview() {
    SendMagicPacketButton("MAC", BROADCAST_ADDRESS, MAGIC_PACKET_UDP_PORT) { }
}