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
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentItem
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentListScreen
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentListViewModel
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentUIModel
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentUiState
import pl.inpost.recruitmenttask.presentation.ui.theme.InpostAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ShipmentListViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            InpostAppTheme {
                ShipmentListScreen(
                    uiState = uiState,
                    onSortByStatus = viewModel::onSortByStatus,
                    onSortByNumber = viewModel::onSortByNumber,
                    onSortByPickupDate = viewModel::onSortByPickupDate,
                    onSortByExpireDate = viewModel::onSortByExpireDate,
                    onSortByStoredDate = viewModel::onSortByStoredDate,
                    onArchiveItem = viewModel::onArchiveItem,
                    modifier = Modifier
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
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(uiState.shipmentList) { _, shipmentUIModel ->
            ShipmentItem(
                shipmentUIModel = shipmentUIModel,
                onArchiveItem = onArchiveItem,
                modifier = modifier
            )
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
                    ShipmentUIModel(
                        shipmentNumber = "235678654323567889762231",
                        status = "Wydana do doręczenia",
                        sender = "Serhii Radkivskyi",
                        date = "pn. | 20.04.24 | 17:42"
                    ),
                    ShipmentUIModel(
                        shipmentNumber = "235678654323567889762231",
                        status = "Wydana do doręczenia",
                        sender = "Serhii Radkivskyi",
                        date = "pn. | 20.04.24 | 17:42"
                    )
                )
            )
        )
    }
}