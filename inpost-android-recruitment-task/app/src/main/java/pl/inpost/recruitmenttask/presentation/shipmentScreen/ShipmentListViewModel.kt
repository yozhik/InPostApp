package pl.inpost.recruitmenttask.presentation.shipmentScreen

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
import pl.inpost.recruitmenttask.data.utils.getErrorMessage
import pl.inpost.recruitmenttask.data.utils.getSuccessDataOrNull
import pl.inpost.recruitmenttask.data.utils.isSuccess
import pl.inpost.recruitmenttask.domain.repository.ArchiveRepository
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.presentation.shipmentScreen.mapper.toUIModel
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUIType
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUiState
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
        refreshData()
    }

    fun onRefresh() {
        _uiState.update {
            it.copy(
                isSwipeToRefreshLoading = true
            )
        }
        refreshData()
    }

    fun onSortByStatus() {
        lastUserAction = LastUserAction.STATUS

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStatus()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Status"))
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    fun onSortByNumber() {
        lastUserAction = LastUserAction.NUMBER

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByNumber()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(0, ShipmentUIType.DividerModel("Sorted by Number"))
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }

                    hideLoading()
                }
        }
    }

    fun onSortByPickupDate() {
        lastUserAction = LastUserAction.PICKUP_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByPickupDate()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(
                                0,
                                ShipmentUIType.DividerModel("Sorted by Pickup Date")
                            )
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    fun onSortByExpireDate() {
        lastUserAction = LastUserAction.EXPIRE_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByExpiredDate()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(
                                0,
                                ShipmentUIType.DividerModel("Sorted by Expire Date")
                            )
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    fun onSortByStoredDate() {
        lastUserAction = LastUserAction.STORED_DATE

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedShipmentsByStoredDate()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(
                                0,
                                ShipmentUIType.DividerModel("Sorted by Stored Date")
                            )
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    fun onArchiveItem(id: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            archiveRepository.archiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onUnArchiveItem(id: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            archiveRepository.unArchiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onShowArchivedShipments() {
        lastUserAction = LastUserAction.ARCHIVED

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getAllArchivedShipments()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let { shipments->
                            shipments.forEach {
                                val shipmentUIModel = it.toUIModel(isArchived = true)
                                shipmentList.add(shipmentUIModel)
                            }
                            shipmentList.add(0, ShipmentUIType.DividerModel("Archived Shipments"))
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    private fun refreshData() {
        lastUserAction = LastUserAction.DEFAULT

        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.Main) {
            showLoading()

            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.loadShipments()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())

                            //TODO: change with some constants and then fetch strings in View
                            shipmentList.add(0, ShipmentUIType.DividerModel("Ready for shipment"))
                            shipmentList.add(
                                shipmentList.size - 1,
                                ShipmentUIType.DividerModel("Pozostale")
                            )
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                    hideLoading()
                }
        }
    }

    private fun repeatLastUserAction() {
        when (lastUserAction) {
            LastUserAction.STATUS -> {
                onSortByStatus()
            }

            LastUserAction.NUMBER -> {
                onSortByNumber()
            }

            LastUserAction.PICKUP_DATE -> {
                onSortByPickupDate()
            }

            LastUserAction.EXPIRE_DATE -> {
                onSortByExpireDate()
            }

            LastUserAction.STORED_DATE -> {
                onSortByStoredDate()
            }

            LastUserAction.ARCHIVED -> {
                onShowArchivedShipments()
            }

            else -> {
                refreshData()
            }
        }
    }


    private suspend fun showLoading() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        //just emulating some delay to show loading, so user would be more aware of the action
        //happening, because soring is happening very fast to be noticed. Can be removed at any moment
        delay(USER_ACTION_FRIENDLY_DELAY)
    }

    private fun hideLoading() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isSwipeToRefreshLoading = false
            )
        }
    }

    private fun updateList(shipmentList: List<ShipmentUIType>) {
        _uiState.update {
            it.copy(
                shipmentList = shipmentList
            )
        }
    }

    private fun showError(errorMessage: String) {
        _uiState.update {
            it.copy(
                errorMessage = errorMessage
            )
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(
                errorMessage = null
            )
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