package pl.inpost.recruitmenttask.presentation.shipmentScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus
import pl.inpost.recruitmenttask.presentation.shipmentScreen.model.ShipmentUIType
import pl.inpost.recruitmenttask.presentation.ui.theme.MontserratFontFamily

@Composable
fun ShipmentItem(
    shipmentUIModel: ShipmentUIType.ShipmentUIModel,
    modifier: Modifier = Modifier,
    onArchiveItem: (String) -> Unit = {},
    onUnArchiveItem: (String) -> Unit = {},
    onOpenDetails: (String) -> Unit = {},
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
            ParcelNumberSection(shipmentUIModel, onArchiveItem, onUnArchiveItem)
            StatusSection(
                shipmentUIModel,
                modifier = modifier.padding(top = 20.dp)
            )
            SenderSection(
                shipmentUIModel,
                onOpenDetails = onOpenDetails,
                modifier = modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun ParcelNumberSection(
    shipmentUIModel: ShipmentUIType.ShipmentUIModel,
    onArchiveItem: (String) -> Unit,
    onUnArchiveItem: (String) -> Unit,
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
                fontWeight = FontWeight.SemiBold,
                fontFamily = MontserratFontFamily
            )
            Text(
                text = shipmentUIModel.shipmentNumber,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                fontFamily = MontserratFontFamily
            )
        }
        Row {
            IconButton(
                onClick = {
                    if (shipmentUIModel.archived) {
                        onUnArchiveItem(shipmentUIModel.shipmentNumber)
                    } else {
                        onArchiveItem(shipmentUIModel.shipmentNumber)
                    }
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = if (shipmentUIModel.archived) {
                        painterResource(id = R.drawable.ic_unarchive)
                    } else {
                        painterResource(id = R.drawable.ic_archive)
                    },
                    contentDescription = if (shipmentUIModel.archived) {
                        stringResource(id = R.string.action_unarchive)
                    } else {
                        stringResource(id = R.string.action_archive)
                    },
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
    shipmentUIModel: ShipmentUIType.ShipmentUIModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    )
    {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(id = R.string.card_status_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontFamily = MontserratFontFamily,
                modifier = Modifier.weight(0.4f)
            )
            if (!shipmentUIModel.date.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = shipmentUIModel.status.nameRes).uppercase(),
                    color = colorResource(id = R.color.ShipmentCardTitleColor),
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MontserratFontFamily,
                    modifier = Modifier.weight(0.6f)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = shipmentUIModel.status.nameRes),
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                fontFamily = MontserratFontFamily,
                modifier = Modifier
                    .weight(0.4f)
                    .wrapContentWidth(align = Alignment.Start)
            )
            if (!shipmentUIModel.date.isNullOrEmpty()) {
                Text(
                    text = shipmentUIModel.date,
                    color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.End,
                    fontFamily = MontserratFontFamily,
                    modifier = Modifier
                        .weight(0.6f)
                        .wrapContentWidth(align = Alignment.End)
                )
            }
        }
    }
}

@Composable
fun SenderSection(
    shipmentUIModel: ShipmentUIType.ShipmentUIModel,
    onOpenDetails: (String) -> Unit,
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
                fontWeight = FontWeight.SemiBold,
                fontFamily = MontserratFontFamily
            )
            Text(
                text = shipmentUIModel.sender,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = MontserratFontFamily
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {
                onOpenDetails(shipmentUIModel.shipmentNumber)
            })
        ) {
            Text(
                text = stringResource(id = R.string.more),
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = MontserratFontFamily,
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
fun ShipmentItemPreview() {
    ShipmentItem(
        shipmentUIModel = ShipmentUIType.ShipmentUIModel(
            shipmentNumber = "235678654323567889762231",
            status = ShipmentStatus.CONFIRMED,
            sender = "Serhii Radkivskyi",
            date = "pn. | 20.04.24 | 17:42"
        )
    )
}
