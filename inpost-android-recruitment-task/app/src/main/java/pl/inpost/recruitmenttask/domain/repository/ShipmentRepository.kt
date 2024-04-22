package pl.inpost.recruitmenttask.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.localStorage.dataSource.ShipmentLocalDataSource
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.localStorage.mappers.toEntities
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.mappers.toDomain
import javax.inject.Inject

class ShipmentRepository @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentLocalDataSource: ShipmentLocalDataSource,
) {
    val shipments: Flow<List<Shipment>> = shipmentLocalDataSource.getAllShipments()
        .map { it.map(ShipmentEntity::toDomain) }

    suspend fun loadShipments() {
        withContext(Dispatchers.IO) {
            val shipments = shipmentApi.getShipments()
            shipmentLocalDataSource.insertItems(shipments.toEntities())
        }
    }

    suspend fun getSortedShipmentsByNumber(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource
                .getSortedShipmentsByNumber()
                .map { it.toDomain() }
        }
    }

    suspend fun getSortedShipmentsByStatus(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource
                .getSortedShipmentsByStatus()
                .map { it.toDomain() }
        }
    }

    suspend fun getSortedShipmentsByStoredDate(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource
                .getSortedShipmentsByStoredDate()
                .map { it.toDomain() }
        }
    }

    suspend fun getSortedShipmentsByExpiredDate(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource.getSortedShipmentsByExpiredDate()
                .map { it.toDomain() }
        }
    }

    suspend fun getSortedShipmentsByPickupDate(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource.getSortedShipmentsByPickupDate()
                .map { it.toDomain() }
        }
    }

    suspend fun getAllArchivedShipments(): Flow<List<Shipment>> {
        return withContext(Dispatchers.IO) {
            delay(300)
            shipmentLocalDataSource.getAllArchivedShipments()
                .map { it.toDomain() }
        }
    }
}