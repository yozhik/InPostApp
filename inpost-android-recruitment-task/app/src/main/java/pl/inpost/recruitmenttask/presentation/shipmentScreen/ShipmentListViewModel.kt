package pl.inpost.recruitmenttask.presentation.shipmentScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.domain.repository.ArchiveRepository
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.presentation.shipmentScreen.mapper.toUIModel
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
    private val archiveRepository: ArchiveRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShipmentUiState())
    val uiState: StateFlow<ShipmentUiState> = _uiState.asStateFlow()

    private var lastUserAction = LastUserAction.DEFAULT
    private var currentJob: Job? = null

    init {
        Log.d("RSD", "init")
        refreshData()
    }

    fun onSortByStatus() {
        Log.d("RSD", "onSortByStatus()")
        lastUserAction = LastUserAction.STATUS

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onSortByStatus")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStatus()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onSortByStatus: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())
                    shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Status"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onSortByStatus")
                }
        }
    }

    fun onSortByNumber() {
        Log.d("RSD", "onSortByNumber()")
        lastUserAction = LastUserAction.NUMBER

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onSortByNumber")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByNumber()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onSortByNumber: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())
                    shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Number"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onSortByNumber")
                }
        }
    }

    fun onSortByPickupDate() {
        Log.d("RSD", "onSortByPickupDate()")
        lastUserAction = LastUserAction.PICKUP_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onSortByPickupDate")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByPickupDate()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onSortByPickupDate: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())
                    shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Pickup Date"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onSortByPickupDate")
                }
        }
    }

    fun onSortByExpireDate() {
        Log.d("RSD", "onSortByExpireDate()")
        lastUserAction = LastUserAction.EXPIRE_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onSortByExpireDate")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByExpiredDate()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onSortByExpireDate: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())
                    shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Expire Date"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onSortByExpireDate")
                }
        }
    }

    fun onSortByStoredDate() {
        Log.d("RSD", "onSortByStoredDate()")
        lastUserAction = LastUserAction.STORED_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onSortByStoredDate")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStoredDate()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onSortByStoredDate: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())
                    shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Stored Date"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onSortByStoredDate")
                }
        }
    }

    fun onArchiveItem(id: String) {
        Log.d("RSD", "onArchiveItem: $id")
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            archiveRepository.archiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onUnArchiveItem(id: String) {
        Log.d("RSD", "onUnArchiveItem: $id")
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            archiveRepository.unArchiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onShowArchivedShipments() {
        Log.d("RSD", "onShowArchivedShipments()")
        lastUserAction = LastUserAction.ARCHIVED

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading("onShowArchivedShipments")
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getAllArchivedShipments()
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "onShowArchivedShipments: ${shipments.size} -> $shipments")
                    shipments.forEach {
                        val shipmentUIModel = it.toUIModel(isArchived = true)
                        shipmentList.add(shipmentUIModel)
                    }
                    shipmentList.add(0, ShipmentUIType.DividerModel("Archived Shipments"))
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading("onShowArchivedShipments")
                }
        }
    }

    private fun refreshData() {
        Log.d("RSD", "refreshData()")
        lastUserAction = LastUserAction.DEFAULT

        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.Main) {
            showLoading("refreshData")

            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.loadShipments()
            shipmentRepository.shipments
                .take(1)
                .collectLatest { shipments ->
                    Log.d("RSD", "on refreshData: ${shipments.size} -> $shipments")
                    shipmentList.addAll(shipments.toUIModel())

                    //TODO: change with some constants and then fetch strings in View
                    shipmentList.add(0, ShipmentUIType.DividerModel("Ready for shipment"))
                    shipmentList.add(
                        shipmentList.size - 1,
                        ShipmentUIType.DividerModel("Pozostale")
                    )

                    withContext(Dispatchers.Main) {
                        _uiState.update {
                            it.copy(
                                shipmentList = shipmentList
                            )
                        }
                        hideLoading("refreshData")
                    }
                }
        }
    }

    private fun repeatLastUserAction() {
        Log.d("RSD", "repeatLastUserAction() entering, lastUserAction: $lastUserAction")
        when (lastUserAction) {
            LastUserAction.STATUS -> {
                Log.d("RSD", "Executing onSortByStatus")
                onSortByStatus()
            }

            LastUserAction.NUMBER -> {
                Log.d("RSD", "Executing onSortByNumber")
                onSortByNumber()
            }

            LastUserAction.PICKUP_DATE -> {
                Log.d("RSD", "Executing onSortByPickupDate")
                onSortByPickupDate()
            }

            LastUserAction.EXPIRE_DATE -> {
                Log.d("RSD", "Executing onSortByExpireDate")
                onSortByExpireDate()
            }

            LastUserAction.STORED_DATE -> {
                Log.d("RSD", "Executing onSortByStoredDate")
                onSortByStoredDate()
            }

            LastUserAction.ARCHIVED -> {
                Log.d("RSD", "Executing onShowArchivedShipments")
                onShowArchivedShipments()
            }

            else -> {
                Log.d("RSD", "Executing refreshData")
                refreshData()
            }
        }
    }


    private suspend fun showLoading(from: String) {
        Log.d("RSD", "      showLoading: $from")
        _uiState.update {
            it.copy(isLoading = true)
        }

        //just emulating some delay to show loading, so user would be more aware of the action
        //happening, because soring is happening very fast to be noticed. Can be removed at any moment
        delay(USER_ACTION_FRIENDLY_DELAY)
    }

    private fun hideLoading(from: String) {
        Log.d("RSD", "      hideLoading: $from")
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    companion object {
        private const val USER_ACTION_FRIENDLY_DELAY: Long = 100L
    }
}

enum class LastUserAction {
    DEFAULT,
    STATUS,
    NUMBER,
    PICKUP_DATE,
    EXPIRE_DATE,
    STORED_DATE,
    ARCHIVED
}