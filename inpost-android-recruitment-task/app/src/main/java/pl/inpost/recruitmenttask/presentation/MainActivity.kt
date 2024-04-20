package pl.inpost.recruitmenttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShipmentContent("Android", uiState)
                }
            }
        }
    }
}

@Composable
fun ShipmentContent(name: String, uiState: ShipmentUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Hello $name!",
            )
        }

        itemsIndexed(uiState.shipmentList) { index, listItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Text(text = listItem.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InpostAppTheme {
        ShipmentContent(
            "Android", ShipmentUiState(
                listOf(
                    ShipmentUIModel("1", "Test"),
                    ShipmentUIModel("1", "Test")
                )
            )
        )
    }
}