package pl.inpost.recruitmenttask.presentation.shipmentScreen.model

data class ShipmentUiState(
    val shipmentList: List<ShipmentUIType> = emptyList(),
    val isLoading: Boolean = false,
    val isSwipeToRefreshLoading: Boolean = false,
    val errorMessage: String? = null,
)