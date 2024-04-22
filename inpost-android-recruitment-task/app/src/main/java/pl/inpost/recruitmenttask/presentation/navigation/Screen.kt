package pl.inpost.recruitmenttask.presentation.navigation

sealed class Screen(val route: String) {
    data object ShipmentList : Screen("shipment_list_screen")
    data object ShipmentDetails : Screen("shipment_details_screen")
}