package pl.inpost.recruitmenttask.presentation.shipmentScreen

import android.util.Log
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
import pl.inpost.recruitmenttask.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.presentation.shipmentScreen.mapper.toUIModel
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShipmentUiState())
    val uiState: StateFlow<ShipmentUiState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun onSortByStatus() {
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
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onArchiveItem: $id")
    }

    private fun refreshData() {
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