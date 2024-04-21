package pl.inpost.recruitmenttask.domain.repository

import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import javax.inject.Inject

class ShipmentRepository @Inject constructor(
    private val shipmentApi: ShipmentApi
) {
    suspend fun getShipments(): List<ShipmentNetwork> = shipmentApi.getShipments()
}