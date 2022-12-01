

package com.lwt.realestatemanager.screens.simulator.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    widthDp = 500,
)
@Composable
fun MonthlyPaymentTitlePreview(){
    RealEstateManagerTheme {
        MonthlyPaymentTitle()
    }
}

@Composable
fun MonthlyPaymentTitle() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    //Margin for all text
    val textModifier = Modifier.padding(2.dp)

    val textStyle = TextStyle(
        color = MaterialTheme.colors.onPrimary,
        fontSize = if (screenWidth >= 500.dp) 22.sp else 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.primary)
            .padding(0.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(id = R.string.title_month),
            fontSize = 12.sp,
            modifier = textModifier.rotate(-90f).weight(1f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )

        Text(
            text = stringResource(id = R.string.title_debt),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )

        Text(
            text = stringResource(id = R.string.title_interest),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )

        Text(
            text = stringResource(id = R.string.title_total_payment),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )

        Text(
            text = stringResource(id = R.string.title_balance),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
    }
}