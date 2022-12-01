package com.lwt.realestatemanager.screens.simulator.initial

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.screens.simulator.info.IconButtonSmall
import com.lwt.realestatemanager.screens.simulator.info.InfoDialog
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme
import com.lwt.realestatemanager.screens.simulator.utils.Constants
import com.lwt.realestatemanager.screens.simulator.utils.isRuCurrLocale

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun InitialContentPreview(){
    RealEstateManagerTheme {
        InitialContent(
            onLoanCardClick = {},
            onInfoClick = {}
        )
    }
}

@Composable
fun InitialScreen(
    navController: NavController
){
    val dialogState = remember { mutableStateOf(false)}
    Column {

        Spacer(modifier = Modifier.padding(10.dp))

        InitialContent(
            onLoanCardClick = {
                navController.navigate("${Constants.DEST_CALCULATION}/$it")
            },
            onInfoClick = {
                dialogState.value = true
            }
        )
    }

    InfoDialog(
        state = dialogState
    ) {
        dialogState.value = false
    }
}




/**Method shows all available loan types*/
@Composable
fun InitialContent(
    onLoanCardClick: (Boolean) -> Unit,
    onInfoClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        /**Row with title and info*/
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                .fillMaxWidth()
        ) {
            //Title
            Text(
                text = stringResource(id = R.string.title_select_loan_type),
                color = MaterialTheme.colors.onPrimary,
                fontSize = if (isRuCurrLocale()) 22.sp else 28.sp,
                textAlign = TextAlign.Start ,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = AbsoluteCutCornerShape(0.dp, 0.dp, 50.dp, 0.dp)
                    )
                    .padding(20.dp, 10.dp, 70.dp, 10.dp)
            )
            //Info button
            IconButtonSmall(
                icon = painterResource(id = R.drawable.ic_info),
                onClick = onInfoClick
            )
            Spacer(modifier = Modifier.padding(1.dp))
        }


        /**Loans*/
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(24.dp))

            /**Annuity loan type*/
            LoanCard(
                title = stringResource(id = R.string.title_annuity),
                description = stringResource(id = R.string.description_annuity),
                hint = stringResource(id = R.string.hint_annuity),
                painterResource(id = R.drawable.ic_chart_flat),
                true
            ){
                onLoanCardClick(it)
            }

            Spacer(modifier = Modifier.padding(20.dp))

            /**Differential loan type*/
            LoanCard(
                title = stringResource(id = R.string.title_differential),
                description = stringResource(id = R.string.description_differential),
                hint = " ",
                painterResource(id = R.drawable.ic_chart_downtrend),
                false
            ){
                onLoanCardClick(it)
            }

            Spacer(modifier = Modifier.padding(30.dp))
        }
    }
}