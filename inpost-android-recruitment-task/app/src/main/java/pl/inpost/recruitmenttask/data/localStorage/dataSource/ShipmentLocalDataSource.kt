package pl.inpost.recruitmenttask.data.localStorage.dataSource

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.localStorage.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import javax.inject.Inject

class ShipmentLocalDataSource @Inject constructor(
    private val shipmentDao: ShipmentDao,
) {
    fun getAllShipments(): Flow<List<ShipmentEntity>> = shipmentDao.getAllShipments()
    fun getSortedShipmentsByNumber(): Flow<List<ShipmentEntity>> =
        shipmentDao.getSortedShipmentsByNumber()

    fun getSortedShipmentsByStatus(): Flow<List<ShipmentEntity>> =
        shipmentDao.getSortedShipmentsByStatus()

    fun getSortedShipmentsByStoredDate(): Flow<List<ShipmentEntity>> =
        shipmentDao.getSortedShipmentsByStoredDate()

    fun getSortedShipmentsByExpiredDate(): Flow<List<ShipmentEntity>> =
        shipmentDao.getSortedShipmentsByExpiredDate()

    fun getSortedShipmentsByPickupDate(): Flow<List<ShipmentEntity>> =
        shipmentDao.getSortedShipmentsByPickupDate()

    suspend fun insertItems(shipmentItems: List<ShipmentEntity>) =
        shipmentDao.insertShipments(shipmentItems)

    fun getAllArchivedShipments(): Flow<List<ShipmentEntity>> {
        return shipmentDao.getAllArchivedShipments()
    }
}