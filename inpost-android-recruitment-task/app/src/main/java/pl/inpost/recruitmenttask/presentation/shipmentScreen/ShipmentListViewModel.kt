package pl.inpost.recruitmenttask.presentation.shipmentScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShipmentUiState())
    val uiState: StateFlow<ShipmentUiState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun onSortByStatus() {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onSortByStatus")
    }

    fun onSortByNumber() {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onSortByNumber")
    }

    fun onSortByPickupDate() {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onSortByPickupDate")
    }

    fun onSortByExpireDate() {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onSortByExpireDate")
    }

    fun onSortByStoredDate() {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onSortByStoredDate")
    }

    fun onArchiveItem(id: String) {
        //TODO: implement
        Log.d("RSD", "ShipmentListViewModel.onArchiveItem: $id")
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            showLoading()
            val shipments = shipmentApi.getShipments()

            val shipmentList = mutableListOf<ShipmentUIModel>()
            withContext(Dispatchers.Default) {
                shipments.forEach { shipment ->
                    shipmentList.add(
                        ShipmentUIModel(
                            shipmentNumber = shipment.number,
                            status = shipment.status,
                            sender = shipment.sender?.name ?: shipment.sender?.email ?: "",
                            date = shipment.expiryDate?.toString() ?: "",
                        )
                    )
                }
            }

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