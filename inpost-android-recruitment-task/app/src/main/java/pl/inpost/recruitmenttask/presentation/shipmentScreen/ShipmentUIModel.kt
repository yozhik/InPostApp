package pl.inpost.recruitmenttask.presentation.shipmentScreen

import pl.inpost.recruitmenttask.domain.model.ShipmentStatus

sealed interface ShipmentUIType {
    data class ShipmentUIModel(
        val shipmentNumber: String = "",
        val status: ShipmentStatus,
        val sender: String = "",
        val date: String? = null,
    ) : ShipmentUIType

    data class DividerModel(
        val title: String = ""
    ) : ShipmentUIType
}