package pl.inpost.recruitmenttask.domain.model.mappers

import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.utils.timestampToZonedDateTimeUTC
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus

fun List<ShipmentEntity>.toDomain(): List<Shipment> {
    return this.map { it.toDomain() }
}

fun ShipmentEntity.toDomain(): Shipment {
    return Shipment(
        number = number,
        shipmentType = shipmentType,
        status = ShipmentStatus.fromString(status),
        openCode = openCode,
        expiryDate = expiryDateTimestamp?.timestampToZonedDateTimeUTC(),
        storedDate = storedDateTimestamp?.timestampToZonedDateTimeUTC(),
        pickUpDate = pickUpDateTimestamp?.timestampToZonedDateTimeUTC(),
        eventLog = emptyList(), //TODO: finish mapping
        receiver = null,
        sender = null,
        operations = null,
    )
}