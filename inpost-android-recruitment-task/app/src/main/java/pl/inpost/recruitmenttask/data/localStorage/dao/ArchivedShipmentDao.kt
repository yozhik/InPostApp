package pl.inpost.recruitmenttask.data.localStorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.localStorage.entities.ArchivedEntity

@Dao
interface ArchivedShipmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun archiveShipment(shipmentId: ArchivedEntity)

    @Query("SELECT * FROM archived_shipments")
    fun getAllArchivedShipments(): Flow<List<ArchivedEntity>>

    @Delete
    fun unArchiveShipment(shipmentId: ArchivedEntity)
}