package pl.inpost.recruitmenttask.data.network.api

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.network.model.ShipmentType
import pl.inpost.recruitmenttask.data.network.model.ShipmentsResponse
import pl.inpost.recruitmenttask.data.utils.ResultContainer
import java.time.ZonedDateTime
import kotlin.random.Random

class MockShipmentApi(
    @ApplicationContext private val context: Context,
    apiTypeAdapter: ApiTypeAdapter
) : ShipmentApi {

    private val response by lazy {
        try {
            val json = context.resources.openRawResource(R.raw.mock_shipment_api_response_copy)
                .bufferedReader()
                .use { it.readText() }
            //throw IllegalStateException("Failed to parse JSON")
            val jsonAdapter = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(apiTypeAdapter)
                .build()
                .adapter(ShipmentsResponse::class.java)

            val result =
                jsonAdapter.fromJson(json) ?: throw IllegalStateException("Failed to parse JSON")
            ResultContainer.Success(result.shipments)  // Assuming ShipmentsResponse contains a List<ShipmentNetwork>
        } catch (e: Exception) {
            ResultContainer.Error("Failed to parse JSON: ${e.message}", e)
        }
    }

    override suspend fun getShipments(): ResultContainer<List<ShipmentNetwork>> {
        return withContext(Dispatchers.IO) {
            delay(1000)  // Emulating network delay
            response
        }
    }
}

private fun mockShipmentNetwork(
    number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
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
