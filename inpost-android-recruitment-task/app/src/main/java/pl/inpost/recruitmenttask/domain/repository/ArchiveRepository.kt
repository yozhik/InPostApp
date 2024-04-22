package pl.inpost.recruitmenttask.domain.repository

import pl.inpost.recruitmenttask.data.localStorage.dataSource.ArchiveLocalDataSource
import javax.inject.Inject

class ArchiveRepository @Inject constructor(
    private val archiveLocalDataSource: ArchiveLocalDataSource,
) {
    suspend fun archiveShipment(shipmentId: String) {
        archiveLocalDataSource.archiveShipment(shipmentId)
    }

    suspend fun unArchiveShipment(shipmentId: String) {
        archiveLocalDataSource.unArchiveShipment(shipmentId)
    }
}