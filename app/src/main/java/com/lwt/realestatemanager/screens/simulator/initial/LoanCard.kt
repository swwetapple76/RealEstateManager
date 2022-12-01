

package com.lwt.realestatemanager.screens.simulator.initial

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme
import com.lwt.realestatemanager.screens.simulator.utils.isRuCurrLocale

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Composable
fun LoanCardPreview(){
    LocalContext.current
    RealEstateManagerTheme {
        LoanCard(
            title = stringResource(id = R.string.title_annuity),
            description = stringResource(id = R.string.description_annuity),
            hint = stringResource(id = R.string.hint_annuity),
            painterResource(id = R.drawable.ic_chart_flat),
            true
        ){
            Log.i("LCardPrev isAnnuity: ", it.toString())
        }
    }
}

@Composable
fun LoanCard(
    title: String,
    description: String,
    hint: String,
    icon: Painter,
    isAnnuityLoan: Boolean,
    onClick: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.loan_card_margin_horizontal), 0.dp)
            .fillMaxWidth()
            //pass loan type onClick
            .clickable { onClick(isAnnuityLoan) },
        shape = AbsoluteRoundedCornerShape(dimensionResource(id = R.dimen.loan_card_corner_radius)),
        backgroundColor = MaterialTheme.colors.background,
        elevation = dimensionResource(id = R.dimen.m_default_elevation)
    ) {

        Column {
            /**Loan Type*/
            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary,
                fontSize = if (isRuCurrLocale()) 22.sp else 28.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    //margin
                    .padding(
                        0.dp,
                        dimensionResource(id = R.dimen.loan_card_padding_vertical),
                        80.dp,
                        0.dp
                    )
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = AbsoluteCutCornerShape(0.dp, 0.dp, 40.dp, 0.dp)
                    )
                    .fillMaxWidth()
                    //padding
                    .padding(16.dp, 5.dp, 50.dp, 5.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.padding(20.dp))

            /**Loan Image*/
            Image(
                painter = icon,
                contentDescription = "",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.loan_card_image_size))
                    .fillMaxSize()
                    .align(alignment = Alignment.CenterHorizontally)
            )



            Divider(
                color = MaterialTheme.colors.primary,
                thickness = dimensionResource(id = R.dimen.loan_card_divider_thickness),
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.loan_card_padding_horizontal), 50.dp,
                    dimensionResource(id = R.dimen.loan_card_padding_horizontal), 0.dp
                )
            )

            /**Description*/
            Text(
                text = description,
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.loan_card_padding_horizontal), 5.dp)
                    .fillMaxWidth()
            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = dimensionResource(id = R.dimen.loan_card_divider_thickness),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.loan_card_padding_horizontal), 0.dp)
            )


            /**Hint with Button*/
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.loan_card_padding_horizontal),
                        10.dp,
                        dimensionResource(id = R.dimen.loan_card_padding_horizontal),
                        dimensionResource(id = R.dimen.loan_card_padding_vertical)
                    )
                    .fillMaxWidth()
            ) {
                /**Hint*/
                Text(
                    text = hint,
                    color = MaterialTheme.colors.secondary,
                    fontSize = if (isRuCurrLocale()) 14.sp else 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 0.dp)
                        .weight(2f)
                )
                /**Button*/
                Text(
                    text = stringResource(id = R.string.action_select),
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = AbsoluteRoundedCornerShape(4.dp)
                        )
                        .padding(16.dp, 10.dp)
                        .weight(1.3f)
                )
            }
        }
    }
}