package dev.maerkle.swola

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import dev.maerkle.swola.network.MAC_REGEX
import dev.maerkle.swola.repository.WakeOnLanNetworkDevice
import dev.maerkle.swola.repository.WoLNetworkDevicesDatabase
import dev.maerkle.swola.ui.HomeScreen
import dev.maerkle.swola.ui.SwolaColors

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
            var isConnectedToNetwork by remember { mutableStateOf(true) }

            val context = LocalContext.current
            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

            DisposableEffect(applicationContext) {

                val networkCallback = object : NetworkCallback() {
                    override fun onCapabilitiesChanged(
                        network: Network,
                        capabilities: NetworkCapabilities
                    ) {
                        println(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                        isConnectedToNetwork = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        println(isConnectedToNetwork)
                    }
                }

                val networkRequest = NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build()
                connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

                onDispose {
                    connectivityManager.unregisterNetworkCallback(networkCallback)
                    Log.d("HomeScreenNetwork", "Network callback unregistered.")
                }
            }

            HomeScreen(
                isConnectedToNetwork,
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