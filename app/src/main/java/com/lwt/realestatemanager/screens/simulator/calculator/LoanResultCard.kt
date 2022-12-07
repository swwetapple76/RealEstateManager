package com.lwt.realestatemanager.screens.simulator.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.screens.home.simulator.LoanResult
import com.lwt.realestatemanager.screens.simulator.utils.isRuCurrLocale
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Composable
fun LoanResultCardPreview() {
    RealEstateManagerTheme {
        val loanResult = LoanResult(
            true,
            100000000.123f,
            122000000.456f,
            48,
            25,
            22,
            emptyList()
        )

        LoanResultCard(loanResult = loanResult) {

        }
    }
}


@Composable
fun LoanResultCard(
    loanResult: LoanResult,
    onClick: () -> Unit,
) {

    if (loanResult.period > 0) {

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = AbsoluteRoundedCornerShape(4.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = dimensionResource(id = R.dimen.m_default_elevation)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                //Total title
                Text(
                    text = stringResource(id = R.string.title_total),
                    color = MaterialTheme.colors.primary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(0.dp, 8.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                )

                /**Styles for texts*/
                val titleTextStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = if (isRuCurrLocale()) 20.sp else 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
                val valueTextStyle = TextStyle(
                    color = MaterialTheme.colors.secondary,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
                val valueTextModifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp)



                Spacer(modifier = Modifier.padding(0.dp, 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    /**Loan amount info*/
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_loan_amount),
                            style = titleTextStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = String.format("%,.2f", loanResult.amountInitial),
                            style = valueTextStyle,
                            modifier = valueTextModifier
                        )
                    }

                    /**Payout amount info*/
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_payout_amount),
                            style = titleTextStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = String.format("%,.2f", loanResult.amountFinal),
                            style = valueTextStyle,
                            modifier = valueTextModifier
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(0.dp, 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    /**Gross interest info*/
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_interest_gross),
                            style = titleTextStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = loanResult.interestGross.toString() + "%",
                            style = valueTextStyle,
                            modifier = valueTextModifier
                        )
                    }

                    /**Gross interest info*/
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_interest_net),
                            style = titleTextStyle,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = loanResult.interestNet.toString() + "%",
                            style = valueTextStyle,
                            modifier = valueTextModifier
                        )
                    }
                }


                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = 1.dp,
                    modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 10.dp)
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    /**Period info*/
                    Text(
                        text =
                        "${stringResource(id = R.string.title_period)}: ${loanResult.period} ${
                            stringResource(id = R.string.title_months)
                        }",
                        style = titleTextStyle,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1.6f)
                    )

                    /**Schedule button*/
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        shape = AbsoluteRoundedCornerShape(4.dp),
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_payment_schedule),
                            fontSize = 16.sp,
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }
        }
    }
}