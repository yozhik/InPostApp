package pl.inpost.recruitmenttask.presentation.shipmentScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.presentation.ShipmentContent
import pl.inpost.recruitmenttask.presentation.ui.theme.LoadingIndicator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreen(
    uiState: ShipmentUiState,
    onSortByStatus: () -> Unit,
    onSortByNumber: () -> Unit,
    onSortByPickupDate: () -> Unit,
    onSortByExpireDate: () -> Unit,
    onSortByStoredDate: () -> Unit,
    onArchiveItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Shipments") },
                    actions = {
                        IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                            Icon(Icons.Default.MoreVert, "")
                        }

                        DropdownMenu(
                            expanded = mDisplayMenu,
                            onDismissRequest = { mDisplayMenu = false }
                        ) {

                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_sort_by_status)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onSortByStatus()
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_sort_by_number)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onSortByNumber()
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_sort_by_pickup_date)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onSortByPickupDate()
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_sort_by_expire_date)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onSortByExpireDate()
                                })
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_sort_by_stored_date)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onSortByStoredDate()
                                })
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.OrangeColor),
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ShipmentContent(
                    uiState = uiState,
                    onArchiveItem = onArchiveItem
                )

                if (uiState.isLoading) {
                    LoadingIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                        indicatorColor = Color.Yellow,
                        strokeWidth = 4.dp
                    )
                }
            }
        }
    }
}