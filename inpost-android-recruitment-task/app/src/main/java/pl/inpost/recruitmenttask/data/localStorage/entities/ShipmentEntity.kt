package pl.inpost.recruitmenttask.data.localStorage.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.localStorage.db.TypeConverter
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork

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

    // This fields are not used in the app, so decided not to implement it as separate tables
    @TypeConverters(TypeConverter::class)
    @Embedded(prefix = "sender_")
    val sender: CustomerNetwork?,
    @Embedded(prefix = "receiver_")
    val receiver: CustomerNetwork?,
    @Embedded(prefix = "operations_")
    val operations: OperationsNetwork?
)