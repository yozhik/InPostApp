package pl.inpost.recruitmenttask.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.inpost.recruitmenttask.presentation.shipmentDetailsScreen.ShipmentDetailsScreen
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentListScreen
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUIType
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUiState

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    uiState: ShipmentUiState,
    onRefresh: () -> Unit,
    onSortByStatus: () -> Unit,
    onSortByNumber: () -> Unit,
    onSortByPickupDate: () -> Unit,
    onSortByExpireDate: () -> Unit,
    onSortByStoredDate: () -> Unit,
    onShowArchivedShipments: () -> Unit,
    onArchiveItem: (String) -> Unit,
    onUnArchiveItem: (String) -> Unit,
) {
    NavHost(
        navController,
        startDestination = Screen.ShipmentList.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(250, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(250, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        composable(Screen.ShipmentList.route) {
            ShipmentListScreen(
                uiState = uiState,
                onRefresh = onRefresh,
                onSortByStatus = onSortByStatus,
                onSortByNumber = onSortByNumber,
                onSortByPickupDate = onSortByPickupDate,
                onSortByExpireDate = onSortByExpireDate,
                onSortByStoredDate = onSortByStoredDate,
                onShowArchivedShipments = onShowArchivedShipments,
                onArchiveItem = onArchiveItem,
                onUnArchiveItem = onUnArchiveItem,
                onOpenDetails = { shipmentId ->
                    navController.navigate("${Screen.ShipmentDetails.route}/$shipmentId")
                },
                modifier = Modifier
            )
        }
        composable(
            "${Screen.ShipmentDetails.route}/{shipmentId}",
            arguments = listOf(navArgument("shipmentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val shipmentId = backStackEntry.arguments?.getString("shipmentId")

            //it's workaround, just to pass less data to the screen, just to show that we can pass data to the screen
            //normally we need to pass specific object or ideally just ID and fetch data inside
            //screen's view model
            val model = uiState.shipmentList.find {
                it is ShipmentUIType.ShipmentUIModel
                        && it.shipmentNumber == shipmentId
            } as ShipmentUIType.ShipmentUIModel

            ShipmentDetailsScreen(
                onBackClicked = { navController.navigateUp() },
                model = model,
            )
        }
    }
}