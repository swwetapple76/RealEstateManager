package com.lwt.realestatemanager.screens.editestate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.R
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.screens.commons.OutlinedDropDown
import com.lwt.realestatemanager.screens.commons.TopBarReturn
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme
import java.text.NumberFormat
import java.util.*


@ExperimentalComposeUiApi
@ExperimentalCoilApi
class EditEstateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = this.intent.extras?.getString("title") ?: "Estate Edit"
        setContent {
            var estate by remember {
                mutableStateOf(this.intent.extras?.getParcelable<Estate>("estate")!!,
                    policy = neverEqualPolicy())
            }
            RealEstateManagerTheme {
                TopBarReturn(this, title) {
                    Box {
                        EditEstateMain(estate, onEstateChange = { estate = it })


                        // Save Float Button

                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            onClick = {
                                val result = Intent().putExtra("estateReturn",
                                    estate)
                                setResult(RESULT_OK, result)
                                finish()
                            }) {
                            Icon(Icons.Filled.Save, "Save current Estate", tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
private fun EditEstateMain(estate: Estate, onEstateChange: (Estate) -> Unit) {
    val configuration = LocalConfiguration.current
    val small = configuration.screenWidthDp <= 450


    Column(Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {


        // Photo List

        Text(
            text = stringResource(R.string.media),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        EditEstatePhotoList(estate, onEstateChange = { onEstateChange(it) })

        // Realty detail

        Row(modifier = Modifier.fillMaxSize()) {
            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedDropDown(label = "Type", currentSelected = estate.type) {
                    estate.type = it
                }
            }

            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextField(
                    label = { Text(stringResource(R.string.district)) },
                    value = estate.district,
                    onValueChange = { estate.district = it; onEstateChange(estate) },

                    )
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {

                OutlinedTextFieldLazy(leadingIcon = Icons.Default.SettingsOverscan,
                    title = stringResource(R.string.surface),
                    value = estate.surface.toEditString(),
                    keyboardType = KeyboardType.Number) {
                    val new = it.toIntOrNull()
                    estate.surface = new ?: -1
                    onEstateChange(estate)
                }
            }
            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextFieldLazy(leadingIcon = Icons.Default.ManageAccounts,
                    title = stringResource(R.string.agent),
                    value = estate.agent) {
                    estate.agent = it
                    onEstateChange(estate)
                }
            }

        }
        Row(modifier = Modifier.fillMaxSize()) {

            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextFieldLazy(leadingIcon = Icons.Default.Home,
                    title = stringResource(R.string.rooms),
                    value = estate.rooms.toEditString(),
                    keyboardType = KeyboardType.Number) {
                    val new = it.toIntOrNull()
                    estate.rooms = new ?: -1
                    onEstateChange(estate)
                }
            }

            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextFieldLazy(leadingIcon = Icons.Default.BedroomParent,
                    title = stringResource(R.string.bedrooms),
                    value = estate.bedrooms.toEditString(),
                    keyboardType = KeyboardType.Number) {
                    val new = it.toIntOrNull()
                    estate.bedrooms = new ?: -1
                    onEstateChange(estate)
                }
            }
            Column(Modifier
                .weight(1.0f)
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextFieldLazy(leadingIcon = Icons.Default.Bathtub,
                    title = stringResource(R.string.bathrooms),
                    value = estate.bathrooms.toEditString(),
                    keyboardType = KeyboardType.Number) {
                    val new = it.toIntOrNull()
                    estate.bathrooms = new ?: -1
                    onEstateChange(estate)
                }
            }
        }
        OutlinedTextField(
            label = { Text(stringResource(R.string.description)) },
            value = estate.description,
            onValueChange = { estate.description = it; onEstateChange(estate) },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            OutlinedTextFieldLazy(leadingIcon = Icons.Default.Place,
                title = stringResource(R.string.location),
                value = estate.address) {
                estate.address = it
                onEstateChange(estate)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            OutlinedTextFieldLazy(leadingIcon = Icons.Default.Interests,
                title = stringResource(R.string.point_of_interest),
                value = estate.interest) {
                estate.interest = it
                onEstateChange(estate)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            OutlinedTextFieldLazy(
                leadingIcon = Icons.Default.Paid,
                title = stringResource(R.string.price),
                value = estate.price.toEditString(),
                keyboardType = KeyboardType.Number) {
                val new = it.toIntOrNull()
                estate.price = new ?: -1
                format.format(estate.price)
                onEstateChange(estate)

            }




        }


    }

}
@Composable
private fun Int.toEditString() = takeIf { it > -1 }?.toString() ?: ""






