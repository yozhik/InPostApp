package pl.inpost.recruitmenttask.presentation.shipmentScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.presentation.ui.theme.MontserratFontFamily

@Composable
fun DividerItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.ListBgColor))
    ) {
        HorizontalDivider(
            modifier = modifier
                .weight(1f)
                .width(1.dp)
                .padding(start = 12.dp, end = 12.dp),
            color = colorResource(id = R.color.DividerColor)
        )
        Text(
            text = title, color = colorResource(id = R.color.DelimeterTextColor),
            fontWeight = FontWeight.SemiBold,
            fontFamily = MontserratFontFamily,
        )
        HorizontalDivider(
            modifier = modifier
                .weight(1f)
                .width(1.dp)
                .padding(start = 12.dp, end = 12.dp),
            color = colorResource(id = R.color.DividerColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DividerPreview() {
    DividerItem(
        title = "Title",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    )
}