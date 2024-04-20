package pl.inpost.recruitmenttask.presentation

data class ShipmentUiState(
    val shipmentList: List<ShipmentUIModel> = emptyList(),
    val isLoading: Boolean = false,
)
