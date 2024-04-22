package pl.inpost.recruitmenttask.presentation.shipmentScreen.model

import pl.inpost.recruitmenttask.domain.model.ShipmentStatus

sealed interface ShipmentUIType {
    data class ShipmentUIModel(
        val shipmentNumber: String = "",
        val status: ShipmentStatus,
        val sender: String = "",
        val date: String? = null,
        val archived: Boolean = false,
    ) : ShipmentUIType

    data class DividerModel(
        val title: String = ""
    ) : ShipmentUIType
}