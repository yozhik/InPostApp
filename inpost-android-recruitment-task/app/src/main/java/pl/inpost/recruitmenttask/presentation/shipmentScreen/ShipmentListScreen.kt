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
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.presentation.ShipmentContent
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUiState
import pl.inpost.recruitmenttask.presentation.ui.theme.ErrorDialog
import pl.inpost.recruitmenttask.presentation.ui.theme.LoadingIndicator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreen(
    modifier: Modifier = Modifier,
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
    onOpenDetails: (String) -> Unit,
    onDismissDialog: () -> Unit,
) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            if (!uiState.isSwipeToRefreshLoading) {
                onRefresh()
            }
        }
    }

    LaunchedEffect(uiState.isSwipeToRefreshLoading) {
        if (uiState.isSwipeToRefreshLoading) {
            pullRefreshState.startRefresh()
        } else {
            pullRefreshState.endRefresh()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Shipments") },
                    actions = {
                        IconButton(onClick = { onShowArchivedShipments() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_archive),
                                contentDescription = stringResource(id = R.string.action_show_archived_items),
                                tint = colorResource(id = R.color.OrangeColor),
                            )
                        }

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
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.action_show_archived_items)) },
                                onClick = {
                                    mDisplayMenu = false
                                    onShowArchivedShipments()
                                })
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.white),
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .nestedScroll(pullRefreshState.nestedScrollConnection)
                    .padding(paddingValues)
            ) {
                ShipmentContent(
                    uiState = uiState,
                    onArchiveItem = onArchiveItem,
                    onUnArchiveItem = onUnArchiveItem,
                    onOpenDetails = onOpenDetails,
                )

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = colorResource(id = R.color.OrangeColor),
                    state = pullRefreshState,
                )

                if (uiState.isLoading && !uiState.isSwipeToRefreshLoading) {
                    LoadingIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                        indicatorColor = Color.Yellow,
                        strokeWidth = 4.dp
                    )
                }

                uiState.errorMessage?.let {
                    ErrorDialog(error = it, onDismiss = onDismissDialog)
                }
            }
        }
    }
}