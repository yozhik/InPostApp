package pl.inpost.recruitmenttask.domain.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import pl.inpost.recruitmenttask.data.localStorage.dataSource.ShipmentLocalDataSource
import pl.inpost.recruitmenttask.data.localStorage.entities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.network.model.ShipmentType
import pl.inpost.recruitmenttask.data.utils.ResultContainer
import java.time.ZonedDateTime


@ExperimentalCoroutinesApi
class ShipmentRepositoryTest {

    @Mock
    private lateinit var shipmentApi: ShipmentApi

    @Mock
    private lateinit var shipmentLocalDataSource: ShipmentLocalDataSource

    private lateinit var repository: ShipmentRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ShipmentRepository(shipmentApi, shipmentLocalDataSource)
    }

    @Test
    fun `loadShipments success scenario`() = runTest {
        val mockData = listOf(mockShipmentNetwork())
        val mockDataEntity = listOf(mockShipmentEntity())
        `when`(shipmentApi.getShipments()).thenReturn(ResultContainer.Success(mockData))
        `when`(shipmentLocalDataSource.getAllShipments()).thenReturn(flowOf(mockDataEntity))

        val result = repository.loadShipments().toList()

        verify(shipmentApi).getShipments()
        assertTrue("First element must be Success", result.first() is ResultContainer.Success)
    }

    @Test
    fun `loadShipments failure scenario`() = runTest {
        `when`(shipmentApi.getShipments()).thenReturn(ResultContainer.Error("Network failure"))

        val result = repository.loadShipments().toList()

        verify(shipmentApi).getShipments()
        assertTrue("First element must be Error", result.first() is ResultContainer.Error)
    }
}

fun mockShipmentEntity(
    number: String = "1234567890123456",
    expiryDate: ZonedDateTime? = null,
    storedDate: ZonedDateTime? = null,
    pickupDate: ZonedDateTime? = null,
    openCode: String? = null,
    shipmentType: String = "PARCEL_LOCKER",
    status: String = "DELIVERED",
    sender: CustomerNetwork? = mockCustomerNetwork(),
    receiver: CustomerNetwork? = mockCustomerNetwork(),
    operations: OperationsNetwork? = mockOperationsNetwork()
): ShipmentEntity {
    return ShipmentEntity(
        number = number,
        expiryDateTimestamp = expiryDate?.toEpochSecond(),
        storedDateTimestamp = storedDate?.toEpochSecond(),
        pickUpDateTimestamp = pickupDate?.toEpochSecond(),
        openCode = openCode,
        shipmentType = shipmentType,
        status = status,
        sender = sender,
        receiver = receiver,
        operations = operations
    )
}

private fun mockShipmentNetwork(
    number: String = "1234567890123456",
    type: ShipmentType = ShipmentType.PARCEL_LOCKER,
    status: ShipmentStatus = ShipmentStatus.DELIVERED,
    sender: CustomerNetwork? = mockCustomerNetwork(),
    receiver: CustomerNetwork? = mockCustomerNetwork(),
    operations: OperationsNetwork = mockOperationsNetwork(),
    eventLog: List<EventLogNetwork> = emptyList(),
    openCode: String? = null,
    expireDate: ZonedDateTime? = null,
    storedDate: ZonedDateTime? = null,
    pickupDate: ZonedDateTime? = null
) = ShipmentNetwork(
    number = number,
    shipmentType = type.name,
    status = status.name,
    eventLog = eventLog,
    openCode = openCode,
    expiryDate = expireDate,
    storedDate = storedDate,
    pickUpDate = pickupDate,
    receiver = receiver,
    sender = sender,
    operations = operations
)

private fun mockCustomerNetwork(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = CustomerNetwork(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun mockOperationsNetwork(
    manualArchive: Boolean = false,
    delete: Boolean = false,
    collect: Boolean = false,
    highlight: Boolean = false,
    expandAvizo: Boolean = false,
    endOfWeekCollection: Boolean = false
) = OperationsNetwork(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)
