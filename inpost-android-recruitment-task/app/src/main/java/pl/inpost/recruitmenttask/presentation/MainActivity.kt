package pl.inpost.recruitmenttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.presentation.ui.theme.InpostAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ShipmentListViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            InpostAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShipmentContent(uiState)
                }
            }
        }
    }
}

@Composable
fun ShipmentContent(
    uiState: ShipmentUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(uiState.shipmentList) { index, listItem ->
            ShipmentItem(listItem)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InpostAppTheme {
        ShipmentContent(ShipmentUiState(
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