package pl.inpost.recruitmenttask.data.localStorage.dataSource

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.localStorage.dao.ArchivedShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.entities.ArchivedEntity
import javax.inject.Inject

class ArchiveLocalDataSource @Inject constructor(
    private val archivedShipmentDao: ArchivedShipmentDao,
) {
    fun getAllArchivedShipments(): Flow<List<ArchivedEntity>> {
        return archivedShipmentDao.getAllArchivedShipments()
    }

    fun archiveShipment(shipmentId: String) {
        archivedShipmentDao.archiveShipment(ArchivedEntity(shipmentId))
    }

    fun unArchiveShipment(shipmentId: String) {
        archivedShipmentDao.unArchiveShipment(ArchivedEntity(shipmentId))
    }
}