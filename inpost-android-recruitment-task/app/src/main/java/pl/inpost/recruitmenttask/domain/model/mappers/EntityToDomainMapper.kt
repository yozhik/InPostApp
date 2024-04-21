package pl.inpost.recruitmenttask.domain.model.mappers

import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.utils.timestampToZonedDateTimeUTC
import pl.inpost.recruitmenttask.domain.model.Customer
import pl.inpost.recruitmenttask.domain.model.Operations
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
        receiver = receiver?.toDomain(),
        sender = sender?.toDomain(),
        operations = operations?.toDomain(),
    )
}

fun CustomerNetwork.toDomain(): Customer {
    return Customer(
        email = email,
        phoneNumber = phoneNumber,
        name = name
    )
}

fun OperationsNetwork.toDomain(): Operations {
    return Operations(
        manualArchive = manualArchive,
        delete = delete,
        collect = collect,
        highlight = highlight,
        expandAvizo = expandAvizo,
        endOfWeekCollection = endOfWeekCollection
    )
}