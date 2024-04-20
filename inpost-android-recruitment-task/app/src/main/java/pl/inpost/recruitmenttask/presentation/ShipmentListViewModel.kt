package pl.inpost.recruitmenttask.presentation

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