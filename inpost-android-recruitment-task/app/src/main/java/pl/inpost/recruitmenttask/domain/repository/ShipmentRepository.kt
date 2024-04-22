package pl.inpost.recruitmenttask.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
    val shipments: Flow<List<Shipment>> = getSortedShipments(shipmentLocalDataSource::getAllShipments)

    suspend fun loadShipments() {
        val shipments = shipmentApi.getShipments()
        shipmentLocalDataSource.insertItems(shipments.toEntities())
    }

    fun getSortedShipmentsByNumber(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByNumber)
    }

    fun getSortedShipmentsByStatus(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByStatus)
    }

    fun getSortedShipmentsByStoredDate(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByStoredDate)
    }

    fun getSortedShipmentsByExpiredDate(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByExpiredDate)
    }

    fun getSortedShipmentsByPickupDate(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByPickupDate)
    }

    fun getAllArchivedShipments(): Flow<List<Shipment>> {
        return getSortedShipments(shipmentLocalDataSource::getAllArchivedShipments)
    }

    private fun getSortedShipments(getSortedShipmentsFunc: () -> Flow<List<ShipmentEntity>>): Flow<List<Shipment>> {
        return getSortedShipmentsFunc()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }
}