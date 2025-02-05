package dev.maerkle.swola.repository

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlin.random.Random

@Entity(tableName = "wol_network_devices")
data class WakeOnLanNetworkDevice(
    @PrimaryKey
    val id: Int = Random.nextInt(),
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "mac_address")
    var macAddress: String,
    @ColumnInfo(name = "port")
    var port: Int = 9,
    @ColumnInfo(name = "broadcast_address")
    var broadcastAddress: String = "192.168.0.255"
)

@Dao
interface WakeOnLanNetworkDeviceDao {
    @Query("SELECT * FROM wol_network_devices")
    fun getAll(): List<WakeOnLanNetworkDevice>

    @Update
    fun update(device: WakeOnLanNetworkDevice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(device: WakeOnLanNetworkDevice)

    @Delete
    fun delete(device: WakeOnLanNetworkDevice)
}