package pl.inpost.recruitmenttask.presentation.shipmentDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
fun ShipmentDetailsContent(
    shipmentUIModel: ShipmentUIType.ShipmentUIModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Column {
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
                fontFamily = MontserratFontFamily,
            )

            Text(
                text = stringResource(id = R.string.card_status_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontFamily = MontserratFontFamily,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = stringResource(id = shipmentUIModel.status.nameRes),
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                fontFamily = MontserratFontFamily,
            )

            if (!shipmentUIModel.date.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = shipmentUIModel.status.nameRes).uppercase(),
                    color = colorResource(id = R.color.ShipmentCardTitleColor),
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MontserratFontFamily,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Text(
                    text = shipmentUIModel.date,
                    color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = MontserratFontFamily,
                )
            }

            Text(
                text = stringResource(id = R.string.card_sender_capital),
                color = colorResource(id = R.color.ShipmentCardTitleColor),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = MontserratFontFamily,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = shipmentUIModel.sender,
                color = colorResource(id = R.color.ShipmentCardValueBodyColor),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = MontserratFontFamily
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShipmentItemPreview() {
    ShipmentDetailsContent(
        shipmentUIModel = ShipmentUIType.ShipmentUIModel(
            shipmentNumber = "235678654323567889762231",
            status = ShipmentStatus.CONFIRMED,
            sender = "Serhii Radkivskyi",
            date = "pn. | 20.04.24 | 17:42"
        )
    )
}
