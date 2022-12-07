

package com.lwt.realestatemanager.screens.simulator.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.screens.home.simulator.MonthlyPayment
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    widthDp = 500
)
@Composable
fun MonthlyPaymentRowPreview(){
    RealEstateManagerTheme {
        val monthlyPayment = MonthlyPayment(
            1, 1135589.256f, 1250000.123f,
            23855890.456f, 37171201.789f
        )
        val monthlyPayment12 = MonthlyPayment(
            12, 1135589.256f, 1250000.123f,
            23855890.456f, 37171201.789f
        )
        Column {
            MonthlyPaymentRow(monthlyPayment = monthlyPayment)
            MonthlyPaymentRow(monthlyPayment = monthlyPayment12)
        }
    }

}


@Composable
fun MonthlyPaymentRow(
    monthlyPayment: MonthlyPayment
){
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val textModifier = Modifier.padding(2.dp)
    val textStyleRegular = TextStyle(
        fontSize = if (screenWidth >= 500.dp) 16.sp else 12.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )
    val textStyleEndOfYear = TextStyle(
        color = MaterialTheme.colors.onSecondary,
        fontSize = if (screenWidth >= 500.dp) 16.sp else 12.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    val rowModifierEndOfYear = Modifier.background(color = MaterialTheme.colors.secondary)
    val rowModifier: Modifier
    val textStyle: TextStyle

    /**If it's last month in year, will set another style*/
    if (monthlyPayment.month % 12 == 0){
        rowModifier = rowModifierEndOfYear
        textStyle = textStyleEndOfYear
    } else{
        rowModifier = Modifier
        textStyle = textStyleRegular
    }

    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = monthlyPayment.month.toString(),
            modifier = textModifier.weight(1f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
        Text(
            text = String.format("%,.2f", monthlyPayment.mainDebtAmount),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
        Text(
            text = String.format("%,.2f", monthlyPayment.interestAmount),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
        Text(
            text = String.format("%,.2f", monthlyPayment.totalAmount),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
        Text(
            text = String.format("%,.2f", monthlyPayment.remainder),
            modifier = textModifier.weight(2f),
            style = textStyle,
            overflow = TextOverflow.Visible
        )
    }
}