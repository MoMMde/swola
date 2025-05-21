package dev.maerkle.swola

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.Room
import dev.maerkle.swola.network.MAC_REGEX
import dev.maerkle.swola.repository.WakeOnLanNetworkDevice
import dev.maerkle.swola.repository.WoLNetworkDevicesDatabase
import dev.maerkle.swola.ui.HomeScreen

class Swola : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            WoLNetworkDevicesDatabase::class.java, "swola"
        ).allowMainThreadQueries().build()

        val wolNetworkDao = db.getDao()
        val allDevices = wolNetworkDao.getAll()

        setContent {

            var devices by remember { mutableStateOf(allDevices) }
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            HomeScreen(
                devices,
                onDelete = {
                    devices = devices.minus(it)
                    wolNetworkDao.delete(it)
                },
                onCreate = {
                    if (!it.macAddress.matches(MAC_REGEX)) {
                        Toast.makeText(this, "Invalid MAC address", Toast.LENGTH_SHORT).show()
                        return@HomeScreen
                    }
                    devices = devices.plus(it)
                    wolNetworkDao.insert(it)
                    println(wolNetworkDao.getAll())
                },
                onEdit = { item ->
                    if (!item.macAddress.matches(MAC_REGEX)) {
                        Toast.makeText(this, "Invalid MAC address", Toast.LENGTH_SHORT).show()
                        return@HomeScreen
                    }
                    wolNetworkDao.update(item)
                })
        }
    }
}