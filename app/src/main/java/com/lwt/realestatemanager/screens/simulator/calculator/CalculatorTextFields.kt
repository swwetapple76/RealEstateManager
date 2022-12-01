package com.lwt.realestatemanager.screens.simulator.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.screens.simulator.SharedViewModel
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Composable
fun CalculatorTextFieldsPreview() {
    RealEstateManagerTheme {
        CalculatorTextFields(viewModel = viewModel())

    }
}

@Composable
fun CalculatorTextFields(
    viewModel: SharedViewModel,
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.calculate_field_margin_horizontal), 0.dp)
            .fillMaxWidth()
    ) {

        val amount by viewModel.typedLoanAmount.collectAsState()
        val interest by viewModel.typedLoanInterest.collectAsState()
        val period by viewModel.typedLoanPeriod.collectAsState()
        val gracePeriod by viewModel.typedLoanGracePeriod.collectAsState()
        val initialFee by viewModel.typedLoanInitialFee.collectAsState()
        val isFieldMissed by viewModel.isFieldMissed.collectAsState()


        val textFieldsModifier = Modifier
            .padding(0.dp, 10.dp)
            .fillMaxWidth()
        val tFldKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)

        //Loan amount
        OutlinedTextField(
            value = amount,
            onValueChange = viewModel::setTypedLoanAmount,
            label = { Text(text = stringResource(id = R.string.hint_loan_amount)) },
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
            maxLines = 1,
            isError = isFieldMissed && amount.isEmpty()
        )
        //Interest
        OutlinedTextField(
            value = interest,
            onValueChange = { if (it.length <= 2) viewModel.setTypedLoanInterest(it) },
            label = { Text(text = stringResource(id = R.string.title_interest) + " in %") },
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
            maxLines = 1,
            isError = isFieldMissed && interest.isEmpty()
        )
        //Period
        OutlinedTextField(
            value = period,
            onValueChange = viewModel::setTypedLoanPeriod,
            label = { Text(text = stringResource(id = R.string.hint_period)) },
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
            maxLines = 1,
            isError = isFieldMissed && period.isEmpty()
        )


        /**Optional Part*/
        Row(
            modifier = Modifier
                .padding(0.dp, 30.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = dimensionResource(id = R.dimen.calculate_field_divider_thickness),
                modifier = Modifier.weight(0.2f)
            )

            Text(
                text = stringResource(id = R.string.title_optional),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.8f)
            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = dimensionResource(id = R.dimen.calculate_field_divider_thickness),
                modifier = Modifier.weight(1.2f)
            )
        }

        //Grace period (optional)
        OutlinedTextField(
            value = gracePeriod,
            onValueChange = viewModel::setTypedLoanGracePeriod,
            label = { Text(text = stringResource(id = R.string.hint_grace_period)) },
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
            maxLines = 1,
        )
        //Initial fee (optional)
        OutlinedTextField(
            value = initialFee,
            onValueChange = { if (it.length <= 2) viewModel.setTypedLoanInitialFee(it) },
            label = { Text(text = stringResource(id = R.string.hint_initial_fee)) },
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
            maxLines = 1
        )
    }

}
