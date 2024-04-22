package pl.inpost.recruitmenttask.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.inpost.recruitmenttask.data.localStorage.dataSource.ShipmentLocalDataSource
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.localStorage.mappers.toEntities
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.utils.ResultContainer
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.mappers.toDomain
import javax.inject.Inject

class ShipmentRepository @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentLocalDataSource: ShipmentLocalDataSource,
) {
    fun loadShipments(): Flow<ResultContainer<List<Shipment>>> {
        return flow {
            when (val result = shipmentApi.getShipments()) {
                is ResultContainer.Success -> {
                    try {
                        shipmentLocalDataSource.insertItems(result.data.toEntities())
                        getSortedShipments(shipmentLocalDataSource::getAllShipments).collect {
                            emit(it)
                        }
                    } catch (e: Exception) {
                        emit(ResultContainer.Error("Failed to insert items into the database: ${e.message}", e))
                    }
                }
                is ResultContainer.Error -> {
                    emit(result)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSortedShipmentsByNumber(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByNumber)
    }

    fun getSortedShipmentsByStatus(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByStatus)
    }

    fun getSortedShipmentsByStoredDate(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByStoredDate)
    }

    fun getSortedShipmentsByExpiredDate(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByExpiredDate)
    }

    fun getSortedShipmentsByPickupDate(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getSortedShipmentsByPickupDate)
    }

    fun getAllArchivedShipments(): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipments(shipmentLocalDataSource::getAllArchivedShipments)
    }

    private fun getSortedShipments(getSortedShipmentsFunc: () -> Flow<List<ShipmentEntity>>): Flow<ResultContainer<List<Shipment>>> {
        return getSortedShipmentsFunc()
            .map { ResultContainer.Success(it.toDomain()) }
            .catch {
                ResultContainer.Error(
                    it.message ?: "Unknown exception in ShipmentRepository"
                )
            }
            .flowOn(Dispatchers.IO)
    }
}