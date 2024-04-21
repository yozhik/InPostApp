package pl.inpost.recruitmenttask.data.localStorage.mappers

import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.utils.zonedDateTimeToTimestampUTC

fun List<ShipmentNetwork>.toEntities(): List<ShipmentEntity> {
    return this.map { it.toEntity() }
}

fun ShipmentNetwork.toEntity(): ShipmentEntity {
    return ShipmentEntity(
        number = number,
        shipmentType = shipmentType,
        status = status,
        openCode = openCode,
        expiryDateTimestamp = expiryDate?.zonedDateTimeToTimestampUTC(),
        storedDateTimestamp = storedDate?.zonedDateTimeToTimestampUTC(),
        pickUpDateTimestamp = storedDate?.zonedDateTimeToTimestampUTC(),
        sender = sender,
        receiver = receiver,
        operations = operations
    )
}