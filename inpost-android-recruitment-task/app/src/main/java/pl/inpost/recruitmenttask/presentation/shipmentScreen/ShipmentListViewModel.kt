package pl.inpost.recruitmenttask.presentation.shipmentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    init {
        refreshData()
    }

    fun onSortByStatus() {
        lastUserAction = LastUserAction.STATUS

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStatus().collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())
                shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Status"))
                _uiState.update {
                    it.copy(
                        shipmentList = shipmentList
                    )
                }
                hideLoading()
            }
        }
    }

    fun onSortByNumber() {
        lastUserAction = LastUserAction.NUMBER

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByNumber().collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())
                shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Number"))
                _uiState.update {
                    it.copy(
                        shipmentList = shipmentList
                    )
                }
                hideLoading()
            }
        }
    }

    fun onSortByPickupDate() {
        lastUserAction = LastUserAction.PICKUP_DATE

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByPickupDate().collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())
                shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Pickup Date"))
                _uiState.update {
                    it.copy(
                        shipmentList = shipmentList
                    )
                }
                hideLoading()
            }
        }
    }

    fun onSortByExpireDate() {
        lastUserAction = LastUserAction.EXPIRE_DATE

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByExpiredDate().collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())
                shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Expire Date"))
                _uiState.update {
                    it.copy(
                        shipmentList = shipmentList
                    )
                }
                hideLoading()
            }
        }
    }

    fun onSortByStoredDate() {
        lastUserAction = LastUserAction.STORED_DATE

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStoredDate().collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())
                shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Stored Date"))
                _uiState.update {
                    it.copy(
                        shipmentList = shipmentList
                    )
                }
                hideLoading()
            }
        }
    }

    fun onArchiveItem(id: String) {
        viewModelScope.launch {
            archiveRepository.archiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onUnArchiveItem(id: String) {
        viewModelScope.launch {
            archiveRepository.unArchiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onShowArchivedShipments() {
        lastUserAction = LastUserAction.ARCHIVED

        viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getAllArchivedShipments().collectLatest { shipments ->
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
                hideLoading()
            }
        }
    }

    private fun refreshData() {
        lastUserAction = LastUserAction.DEFAULT

        viewModelScope.launch(Dispatchers.Main) {
            showLoading()

            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.loadShipments()
            shipmentRepository.shipments.collectLatest { shipments ->
                shipmentList.addAll(shipments.toUIModel())

                //TODO: change with some constants and then fetch strings in View
                shipmentList.add(0, ShipmentUIType.DividerModel("Ready for shipment"))
                shipmentList.add(shipmentList.size - 1, ShipmentUIType.DividerModel("Pozostale"))

                withContext(Dispatchers.Main) {
                    _uiState.update {
                        it.copy(
                            shipmentList = shipmentList
                        )
                    }
                    hideLoading()
                }
            }
        }
    }

    fun repeatLastUserAction() {
        //calling this function to refresh data after archiving/unarchiving item
        when (lastUserAction) {
            LastUserAction.STATUS -> onSortByStatus()
            LastUserAction.NUMBER -> onSortByNumber()
            LastUserAction.PICKUP_DATE -> onSortByPickupDate()
            LastUserAction.EXPIRE_DATE -> onSortByExpireDate()
            LastUserAction.STORED_DATE -> onSortByStoredDate()
            LastUserAction.ARCHIVED -> onShowArchivedShipments()
            else -> refreshData()
        }
    }

    private fun showLoading() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }

    private fun hideLoading() {
        _uiState.update {
            it.copy(isLoading = false)
        }
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