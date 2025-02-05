package dev.maerkle.swola.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WakeOnLanNetworkDevice::class], version = 1)
abstract class WoLNetworkDevicesDatabase : RoomDatabase() {
    abstract fun getDao(): WakeOnLanNetworkDeviceDao
}