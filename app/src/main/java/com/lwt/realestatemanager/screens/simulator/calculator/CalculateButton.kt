package com.lwt.realestatemanager.screens.simulator.calculator

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Composable
fun CalculateButtonPreview() {
    RealEstateManagerTheme {
        CalculateButton {}
    }
}

@Composable
fun CalculateButton(
    onCalculateClick: () -> Unit,
) {
    Button(
        onClick = onCalculateClick,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.calculate_field_margin_horizontal), 0.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        shape = AbsoluteRoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.action_calculate),
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSecondary
        )
    }
}