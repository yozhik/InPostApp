package pl.inpost.recruitmenttask.domain.repository

import android.util.Log
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
    val shipments: Flow<List<Shipment>> = shipmentLocalDataSource.getAllShipments()
        .map { it.map(ShipmentEntity::toDomain) }

    suspend fun loadShipments() {
        Log.d("RSD", "ShipmentRepository.loadShipments()")
        val shipments = shipmentApi.getShipments()
        shipmentLocalDataSource.insertItems(shipments.toEntities())
    }

    fun getSortedShipmentsByNumber(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getSortedShipmentsByNumber()")
        return shipmentLocalDataSource
            .getSortedShipmentsByNumber()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }

    fun getSortedShipmentsByStatus(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getSortedShipmentsByStatus()")
        return shipmentLocalDataSource
            .getSortedShipmentsByStatus()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }

    fun getSortedShipmentsByStoredDate(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getSortedShipmentsByStoredDate()")
        return shipmentLocalDataSource
            .getSortedShipmentsByStoredDate()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }

    fun getSortedShipmentsByExpiredDate(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getSortedShipmentsByExpiredDate()")
        return shipmentLocalDataSource.getSortedShipmentsByExpiredDate()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }

    fun getSortedShipmentsByPickupDate(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getSortedShipmentsByPickupDate()")
        return shipmentLocalDataSource.getSortedShipmentsByPickupDate()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }

    fun getAllArchivedShipments(): Flow<List<Shipment>> {
        Log.d("RSD", "ShipmentRepository.getAllArchivedShipments()")
        return shipmentLocalDataSource.getAllArchivedShipments()
            .map { it.toDomain() }
            .flowOn(Dispatchers.IO)
    }
}