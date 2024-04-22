package pl.inpost.recruitmenttask.presentation.shipmentDetailsScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.presentation.shipmentScreen.ShipmentUIType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentDetailsScreen(
    model: ShipmentUIType.ShipmentUIModel,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Shipment Details") },
                    navigationIcon = {
                        IconButton(onClick = { onBackClicked() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.white),
                    )
                )
            }
        ) { paddingValues ->
            ShipmentDetailsContent(
                shipmentUIModel = model,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}