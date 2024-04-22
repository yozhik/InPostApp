package pl.inpost.recruitmenttask.domain.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.localStorage.dataSource.ArchiveLocalDataSource
import javax.inject.Inject

class ArchiveRepository @Inject constructor(
    private val archiveLocalDataSource: ArchiveLocalDataSource,
) {
    val cachedArchivedShipmentsIds = hashSetOf<String>()

    fun getAllArchivedShipments() {
        Log.d("RSD", "ArchiveRepository.getAllArchivedShipments()")
        if (cachedArchivedShipmentsIds.isEmpty()) {
            archiveLocalDataSource.getAllArchivedShipments().map {
                cachedArchivedShipmentsIds.addAll(it.map { it.shipmentId })
            }
        }
    }

    suspend fun archiveShipment(shipmentId: String) {
        Log.d("RSD", "ArchiveRepository.archiveShipment($shipmentId)")
        withContext(Dispatchers.IO) {
            cachedArchivedShipmentsIds.add(shipmentId)
            archiveLocalDataSource.archiveShipment(shipmentId)
        }
    }

    suspend fun unArchiveShipment(shipmentId: String) {
        Log.d("RSD", "ArchiveRepository.unArchiveShipment($shipmentId)")
        withContext(Dispatchers.IO) {
            cachedArchivedShipmentsIds.remove(shipmentId)
            archiveLocalDataSource.unArchiveShipment(shipmentId)
        }
    }
}