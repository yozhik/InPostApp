package pl.inpost.recruitmenttask.presentation.shipmentScreen

sealed interface ShipmentUIType {
    data class ShipmentUIModel(
        val shipmentNumber: String = "",
        val status: String = "",
        val sender: String = "",
        val date: String = "",
    ) : ShipmentUIType

    data class DividerModel(
        val title: String = ""
    ) : ShipmentUIType
}