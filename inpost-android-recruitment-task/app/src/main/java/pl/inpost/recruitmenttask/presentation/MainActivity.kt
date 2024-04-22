package pl.inpost.recruitmenttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.model.SortType
import pl.inpost.recruitmenttask.presentation.navigation.AppNavigation
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentListViewModel
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUIType
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUiState
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ui.DividerItem
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ui.ShipmentItem
import pl.inpost.recruitmenttask.presentation.ui.theme.InpostAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ShipmentListViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            InpostAppTheme {
                navController = rememberNavController()

                AppNavigation(
                    navController = navController,
                    uiState = uiState,
                    onRefresh = viewModel::onRefresh,
                    onSortByStatus = { viewModel.onSort(SortType.ByStatus) },
                    onSortByNumber = { viewModel.onSort(SortType.ByNumber) },
                    onSortByPickupDate = { viewModel.onSort(SortType.ByPickupDate) },
                    onSortByExpireDate = { viewModel.onSort(SortType.ByExpireDate) },
                    onSortByStoredDate = { viewModel.onSort(SortType.ByStoredDate) },
                    onShowArchivedShipments = viewModel::onShowArchivedShipments,
                    onArchiveItem = viewModel::onArchiveItem,
                    onUnArchiveItem = viewModel::onUnArchiveItem,
                    onDismissDialog = viewModel::clearError,
                )
            }
        }
    }

}

@Composable
fun ShipmentContent(
    uiState: ShipmentUiState,
    modifier: Modifier = Modifier,
    onArchiveItem: (String) -> Unit = {},
    onUnArchiveItem: (String) -> Unit = {},
    onOpenDetails: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(uiState.shipmentList) { _, shipmentUIModel ->
            when (shipmentUIModel) {
                is ShipmentUIType.DividerModel -> DividerItem(title = shipmentUIModel.title)
                is ShipmentUIType.ShipmentUIModel -> {
                    ShipmentItem(
                        shipmentUIModel = shipmentUIModel,
                        onArchiveItem = onArchiveItem,
                        onUnArchiveItem = onUnArchiveItem,
                        onOpenDetails = onOpenDetails,
                        modifier = modifier
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShipmentContentPreview() {
    InpostAppTheme {
        ShipmentContent(
            ShipmentUiState(
                listOf(
                    ShipmentUIType.ShipmentUIModel(
                        shipmentNumber = "235678654323567889762231",
                        status = ShipmentStatus.ADOPTED_AT_SORTING_CENTER,
                        sender = "Serhii Radkivskyi",
                        date = "pn. | 20.04.24 | 17:42"
                    ),
                    ShipmentUIType.ShipmentUIModel(
                        shipmentNumber = "235678654323567889762231",
                        status = ShipmentStatus.PICKUP_TIME_EXPIRED,
                        sender = "Serhii Radkivskyi",
                        date = "pn. | 20.04.24 | 17:42"
                    )
                )
            )
        )
    }
}