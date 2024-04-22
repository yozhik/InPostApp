package pl.inpost.recruitmenttask.presentation.shipmentScreen

data class ShipmentUiState(
    val shipmentList: List<ShipmentUIType> = emptyList(),
    val isLoading: Boolean = false,
    val isSwipeToRefreshLoading: Boolean = false,
)
