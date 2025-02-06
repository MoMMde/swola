package dev.maerkle.swola.ui

import SendMagicPacketButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.maerkle.swola.network.BROADCAST_ADDRESS
import dev.maerkle.swola.network.MAGIC_PACKET_UDP_PORT

@Composable
fun NetworkDeviceBox(deviceName: String, macAddress: String, broadcastAddress: String, port: Int) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val boxHeight = screenHeight / 8


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight)
            .fillMaxHeight(0.7f)
            .clip(RoundedCornerShape(8.dp))
            .background(SwolaColors.BRIGHT_CREME_WHITE)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Devices,
                contentDescription = "Device Icon",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = deviceName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = macAddress,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            SendMagicPacketButton(macAddress, broadcastAddress, port)
        }
    }
}

@Composable
@PreviewLightDark
@PreviewFontScale
@Preview("Unthemed Network Device Preview", showBackground = true)
fun NetworkDevicePreview() {
    NetworkDeviceBox("A sample Device", "aa:bb:cc:dd:ee:ff", BROADCAST_ADDRESS, MAGIC_PACKET_UDP_PORT)
}