package pl.inpost.recruitmenttask.data.network.api

import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.utils.ResultContainer

interface ShipmentApi {
    suspend fun getShipments(): ResultContainer<List<ShipmentNetwork>>
}