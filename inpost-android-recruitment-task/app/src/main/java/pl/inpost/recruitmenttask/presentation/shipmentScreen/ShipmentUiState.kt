package pl.inpost.recruitmenttask.presentation.shipmentScreen

data class ShipmentUiState(
    val shipmentList: List<ShipmentUIModel> = emptyList(),
    val isLoading: Boolean = false,
)
