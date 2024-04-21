package pl.inpost.recruitmenttask.data.localStorage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipments")
data class ShipmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "number") val number: String,

    @ColumnInfo(name = "expiry_date_timestamp") val expiryDateTimestamp: Long?,
    @ColumnInfo(name = "stored_date_timestamp") val storedDateTimestamp: Long?,
    @ColumnInfo(name = "pick_up_date_timestamp") val pickUpDateTimestamp: Long?,
    @ColumnInfo(name = "open_code") val openCode: String?,
    @ColumnInfo(name = "shipment_type") val shipmentType: String,
    @ColumnInfo(name = "status") val status: String,
)