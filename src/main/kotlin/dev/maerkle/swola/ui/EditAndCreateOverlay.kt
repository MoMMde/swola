package dev.maerkle.swola.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import dev.maerkle.swola.network.BROADCAST_ADDRESS
import dev.maerkle.swola.network.MAGIC_PACKET_UDP_PORT

@Composable
fun Overlay(
    name: String,
    onNameChange: (String) -> Unit,
    macAddress: String,
    onMacAddressChange: (String) -> Unit,
    broadcastAddress: String,
    onBroadcastAddressChange: (String) -> Unit,
    port: Int,
    onPortChange: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SwolaColors.DARK_BLUE_COLOR)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.8f)
                .align(Alignment.TopCenter)
        ) {
            SwolaTextInputField(macAddress, onMacAddressChange, "Mac Address", Modifier)
            SwolaTextInputField(name, onNameChange, "Name", Modifier)
            SwolaTextInputField(
                broadcastAddress,
                onBroadcastAddressChange,
                "Broadcast Address",
                Modifier
            )
            SwolaTextInputField(
                port.toString(),
                onValueChange = { portAsString ->
                    if (!portAsString.isDigitsOnly() || portAsString.isEmpty()) {
                        return@SwolaTextInputField
                    }
                    val portAsInt = portAsString.toInt()
                    if (portAsInt < 0 || portAsInt > 65535) {
                        return@SwolaTextInputField
                    }

                    onPortChange(portAsInt)

                },
                "Port",
                Modifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.padding(top = 25.dp))

            content.invoke()
        }
    }
}

@Composable
fun CreateOverlay(
    onCreate: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("A new Network device") }
    var mac by remember { mutableStateOf("aa:bb:cc:dd:ee:ff") }
    var broadcastAddress by remember { mutableStateOf(BROADCAST_ADDRESS) }
    var port by remember { mutableIntStateOf(MAGIC_PACKET_UDP_PORT) }
    Overlay(
        name,
        onNameChange = { name = it },
        mac,
        onMacAddressChange = { mac = it },
        broadcastAddress,
        onBroadcastAddressChange = { broadcastAddress = it },
        port,
        onPortChange = { port = it } ) {
        SwolaButton(
            label = "Create",
            onClick = { onCreate(name, mac, broadcastAddress) },
            backgroundColor = SwolaColors.BRIGHT_GREEN,
            fontColor = Color.White
        )
    }
}

@Composable
fun EditOverlay(
    name: String,
    onNameChange: (String) -> Unit,
    macAddress: String,
    onMacAddressChange: (String) -> Unit,
    broadcastAddress: String,
    onBroadcastAddressChange: (String) -> Unit,
    port: Int,
    onPortChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Overlay(
        name,
        onNameChange,
        macAddress,
        onMacAddressChange,
        broadcastAddress,
        onBroadcastAddressChange,
        port,
        onPortChange
    ) {
        // TODO: Add a BasicAlertDialog https://composables.com/material3/basicalertdialog
        SwolaButton(
            label = "Delete",
            onClick = onDelete,
            backgroundColor = SwolaColors.BRIGHT_RED,
            fontColor = Color.White
        )
    }
}

@Composable
@Preview
fun PreviewEditOverlay() {
    EditOverlay("moritz@home", {}, "aa:bb:cc:dd:ee:ff", {}, "192.168.0.255", {}, 9, {}, {})
}

@Composable
@Preview
fun PreviewCreateOverlay() {
    CreateOverlay { _, _, _ -> }
}