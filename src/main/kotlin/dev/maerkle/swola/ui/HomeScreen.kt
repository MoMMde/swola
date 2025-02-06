package dev.maerkle.swola.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import dev.maerkle.swola.repository.WakeOnLanNetworkDevice

private const val LOG_TAG = "dev.maerkle.swola.ui.HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    devices: List<WakeOnLanNetworkDevice>,
    onCreate: (WakeOnLanNetworkDevice) -> Unit,
    onDelete: (WakeOnLanNetworkDevice) -> Unit,
    onEdit: (WakeOnLanNetworkDevice) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val createSheetState = rememberModalBottomSheetState()
        var createOverlay by remember { mutableStateOf(false) }

        if (createOverlay) {
            ModalBottomSheet(
                containerColor = SwolaColors.DARK_BLUE_COLOR,
                onDismissRequest = { createOverlay = false }, sheetState = createSheetState
            ) {
                CreateOverlay { name, mac, broadcastAddress ->
                    Log.i(LOG_TAG, "Created new device: $name, $mac, $broadcastAddress")
                    createOverlay = false
                    onCreate(
                        WakeOnLanNetworkDevice(
                            name = name,
                            macAddress = mac,
                            broadcastAddress = broadcastAddress
                        )
                    )
                }
            }
        }

        IconButton(
            onClick = {
                createOverlay = true
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = (0.06f * 800).dp, end = (0.06f * 800).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add network devices",
                tint = SwolaColors.BRIGHT_CREME_WHITE,
                modifier = Modifier.size(30.dp * 1.3f)
            )
        }

        Spacer(modifier = Modifier.padding(top = (0.1f * 800).dp))
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(0.87f),
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {

                items(devices) { item ->
                    WakeOnLanDeviceHomeScreen(item, onEdit, onDelete)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WakeOnLanDeviceHomeScreen(
    item: WakeOnLanNetworkDevice,
    onEdit: (WakeOnLanNetworkDevice) -> Unit,
    onDelete: (WakeOnLanNetworkDevice) -> Unit
) {
    val editSheetState = rememberModalBottomSheetState()
    var editOverlay by remember { mutableStateOf(false) }

    Box(Modifier.clickable {
        editOverlay = true
    }) {
        NetworkDeviceBox(
            item.name,
            item.macAddress,
            item.broadcastAddress,
            item.port
        )
        if (editOverlay) {
            ModalBottomSheet(
                containerColor = SwolaColors.DARK_BLUE_COLOR,
                onDismissRequest = {
                    editOverlay = false
                    onEdit(item)
                },
                sheetState = editSheetState
            ) {
                EditOverlay(name = item.name, onNameChange = {
                    item.name = it
                }, macAddress = item.macAddress, onMacAddressChange = {
                    item.macAddress = it
                }, item.broadcastAddress, onBroadcastAddressChange = {
                    item.broadcastAddress = it
                }, item.port, onPortChange = {
                    item.port = it.toInt()
                },
                    onDelete = {
                        onDelete(item)
                        editOverlay = false
                    })
            }
        }
    }
}


@Composable
@Preview
@PreviewScreenSizes
fun PreviewHomeScreen() {
    HomeScreen(emptyList(), {}, {}, {})
}