package dev.maerkle.swola.network

import android.net.MacAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

const val MAGIC_PACKET_UDP_PORT = 9
const val BROADCAST_ADDRESS = "192.168.0.255"

// https://www.amd.com/content/dam/amd/en/documents/archived-tech-docs/white-papers/20213.pdf
suspend fun sendMagicPacket(macAddressAsString: String) {
    // The WoL packet frame starts off with 6 times FF. After this, the mac address will be repeated 16 times
    var buffer = byteArrayOf(
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte()
    )
    val macAddress = MacAddress.fromString(macAddressAsString).toByteArray()
    // Here we add 16 times the mac address to the buffer
    repeat(16) {
        buffer += macAddress
    }

    withContext(Dispatchers.IO) {
        // fire in the hole
        // crafts the packet
        val packet = DatagramPacket(
            buffer, buffer.size, InetAddress.getByName(
                BROADCAST_ADDRESS
            ), MAGIC_PACKET_UDP_PORT
        )

        val socket = DatagramSocket()

        socket.send(packet)
        socket.close()
    }
}