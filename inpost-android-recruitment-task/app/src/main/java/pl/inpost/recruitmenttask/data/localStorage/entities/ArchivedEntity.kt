package pl.inpost.recruitmenttask.data.localStorage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archived_shipments")
data class ArchivedEntity(
    @PrimaryKey
    @ColumnInfo(name = "shipment_id") val shipmentId: String
)
