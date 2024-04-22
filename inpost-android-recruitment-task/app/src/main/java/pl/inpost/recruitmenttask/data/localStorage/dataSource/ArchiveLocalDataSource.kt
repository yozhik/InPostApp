package pl.inpost.recruitmenttask.data.localStorage.dataSource

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.localStorage.dao.ArchivedShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.entities.ArchivedEntity
import javax.inject.Inject

class ArchiveLocalDataSource @Inject constructor(
    private val archivedShipmentDao: ArchivedShipmentDao,
) {
    suspend fun archiveShipment(shipmentId: String) {
        archivedShipmentDao.archiveShipment(ArchivedEntity(shipmentId))
    }

    suspend fun unArchiveShipment(shipmentId: String) {
        archivedShipmentDao.unArchiveShipment(ArchivedEntity(shipmentId))
    }
}