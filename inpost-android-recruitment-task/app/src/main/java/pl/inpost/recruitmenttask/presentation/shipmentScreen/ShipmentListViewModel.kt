package pl.inpost.recruitmenttask.presentation.shipmentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import pl.inpost.recruitmenttask.domain.model.SortType
import pl.inpost.recruitmenttask.domain.repository.ArchiveRepository
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.presentation.shipmentScreen.mapper.toUIModel
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.LastUserAction
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUIType
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUiState
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.toLastUserAction
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.toTitle
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

    fun onSort(sortType: SortType) {
        lastUserAction = sortType.toLastUserAction()
        launchJob {
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getSortedByType(sortType)
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let {
                            shipmentList.addAll(it.toUIModel())
                            shipmentList.add(
                                0,
                                ShipmentUIType.DividerModel(sortType.toTitle())
                            )
                            updateList(shipmentList)
                        }
                    } else {
                        showError(shipmentsResult.getErrorMessage())
                    }
                }
        }
    }

    fun onArchiveItem(id: String) {
        launchJob {
            archiveRepository.archiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onUnArchiveItem(id: String) {
        launchJob {
            archiveRepository.unArchiveShipment(id)
            repeatLastUserAction()
        }
    }

    fun onShowArchivedShipments() {
        lastUserAction = LastUserAction.ARCHIVED

        launchJob {
            val shipmentList = mutableListOf<ShipmentUIType>()
            shipmentRepository.getAllArchivedShipments()
                .take(1)
                .collectLatest { shipmentsResult ->
                    if (shipmentsResult.isSuccess) {
                        shipmentsResult.getSuccessDataOrNull()?.let { shipments ->
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
                }
        }
    }

    private fun refreshData() {
        lastUserAction = LastUserAction.DEFAULT
        launchJob {
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
                }
        }
    }

    private fun repeatLastUserAction() {
        when (lastUserAction) {
            LastUserAction.STATUS -> onSort(SortType.ByStatus)
            LastUserAction.NUMBER -> onSort(SortType.ByNumber)
            LastUserAction.PICKUP_DATE -> onSort(SortType.ByPickupDate)
            LastUserAction.EXPIRE_DATE -> onSort(SortType.ByExpireDate)
            LastUserAction.STORED_DATE -> onSort(SortType.ByStoredDate)
            LastUserAction.ARCHIVED -> onShowArchivedShipments()
            else -> refreshData()
        }
    }

    private fun launchJob(action: suspend () -> Unit) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            showLoading()
            action()
            hideLoading()
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