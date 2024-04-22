package pl.inpost.recruitmenttask.presentation.shipmentScreen.mapper

import pl.inpost.recruitmenttask.data.utils.formatFullShipmentDateTime
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentUIType

fun List<Shipment>.toUIModel(): List<ShipmentUIType.ShipmentUIModel> {
    return this.map { it.toUIModel() }
}

fun Shipment.toUIModel(isArchived:Boolean = false): ShipmentUIType.ShipmentUIModel {
    return ShipmentUIType.ShipmentUIModel(
        shipmentNumber = number,
        status = status,
        sender = sender?.name ?: sender?.email ?: sender?.phoneNumber ?: "",
        date = storedDate?.formatFullShipmentDateTime(),
        archived = isArchived,
    )
}