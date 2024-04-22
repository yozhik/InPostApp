package pl.inpost.recruitmenttask.data.localStorage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.localStorage.dao.ArchivedShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.entities.ArchivedEntity
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity

@Database(entities = [ShipmentEntity::class, ArchivedEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ShipmentDatabase : RoomDatabase() {
    abstract val shipmentDao: ShipmentDao
    abstract val archivedShipmentDao: ArchivedShipmentDao

    companion object {

        private const val DATABASE_NAME = "shipment_database.db"

        @Volatile
        private var INSTANCE: ShipmentDatabase? = null

        operator fun invoke(context: Context): ShipmentDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShipmentDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                    INSTANCE = it
                }
            }
    }
}