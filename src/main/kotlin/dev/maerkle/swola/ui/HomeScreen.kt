package dev.maerkle.swola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.End).padding(top = (0.06f * 800).dp, end = (0.06f * 800).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit network devices",
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
                item {
                    NetworkDeviceBox("moritz@home", "a8:5e:45:56:b5:04")
                }
            }
        }
    }
}


@Composable
@Preview
@PreviewScreenSizes
fun PreviewHomeScreen() {
    HomeScreen()
}