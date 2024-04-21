package pl.inpost.recruitmenttask.data.localStorage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity

@Dao
interface ShipmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipments(shipments: List<ShipmentEntity>)

    @Query("SELECT * FROM shipments")
    fun getAllShipments(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipments ORDER BY number ASC")
    fun getSortedShipmentsByNumber(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipments ORDER BY status ASC")
    fun getSortedShipmentsByStatus(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipments ORDER BY stored_date_timestamp ASC")
    fun getSortedShipmentsByStoredDate(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipments ORDER BY expiry_date_timestamp DESC")
    fun getSortedShipmentsByExpiredDate(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipments ORDER BY pick_up_date_timestamp DESC")
    fun getSortedShipmentsByPickupDate(): Flow<List<ShipmentEntity>>
}