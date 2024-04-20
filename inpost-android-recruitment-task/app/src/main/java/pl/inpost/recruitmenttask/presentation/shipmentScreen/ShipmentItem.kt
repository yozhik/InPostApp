package pl.inpost.recruitmenttask.presentation.shipmentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.inpost.recruitmenttask.R

@Composable
fun ShipmentItem(
    shipmentUIModel: ShipmentUIModel,
    modifier: Modifier = Modifier,
    onArchiveItem: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.ListBgColor))
            .padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(20.dp)
        ) {
            ParcelNumberSection(shipmentUIModel, onArchiveItem)
            StatusSection(
                shipmentUIModel,
                modifier = modifier.padding(top = 20.dp)
            )
            SenderSection(
                shipmentUIModel,
                modifier = modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun ParcelNumberSection(
    shipmentUIModel: ShipmentUIModel,
    onArchiveItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.card_number_of_parcel_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = shipmentUIModel.shipmentNumber,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }
        Row {
            IconButton(
                onClick = { onArchiveItem(shipmentUIModel.shipmentNumber) },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.action_archive),
                    tint = colorResource(id = R.color.OrangeColor)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_kurier_car),
                contentDescription = "Shipment Type",
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 1.dp),
            )
        }

    }
}

@Composable
fun StatusSection(
    shipmentUIModel: ShipmentUIModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    )
    {
        Column {
            Text(
                text = stringResource(id = R.string.card_status_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = shipmentUIModel.status,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = shipmentUIModel.status.uppercase(),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = shipmentUIModel.date,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun SenderSection(
    shipmentUIModel: ShipmentUIModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.card_sender_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = shipmentUIModel.sender,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.more),
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 4.dp, start = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_to_right),
                tint = Color.Unspecified,
                contentDescription = stringResource(id = R.string.more),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShipmentItem(
        shipmentUIModel = ShipmentUIModel(
            shipmentNumber = "235678654323567889762231",
            status = "Wydana do doręczenia",
            sender = "Serhii Radkivskyi",
            date = "pn. | 20.04.24 | 17:42"
        )
    )
}
