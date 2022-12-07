package com.lwt.realestatemanager.screens.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.model.Estate

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun OutlinedFieldFromTo(
	propsContainer: FilterPropertyContainer,
	from: Estate,
	to: Estate,
	onValuesChanged: (Estate, Estate) -> (Unit),
) {
	Row() {
		when {

			// DATE

			propsContainer.dateProps != null -> {
				OutlinedDatePickerButton(
					title = "From",
					onValueChange = {
						propsContainer.dateProps?.set(from, it)
						onValuesChanged(from, to)
					},
					modifier = Modifier.weight(1.0f),
				)

				Spacer(Modifier
					.width(0.dp))

				OutlinedDatePickerButton(
					title = "To",
					onValueChange = {
						propsContainer.dateProps?.set(to, it)
						onValuesChanged(from, to)
					},
					modifier = Modifier
						.weight(1.0f),
				)
			}

			// INT

			propsContainer.intProps != null -> {
				OutlinedTextField(
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					value = propsContainer.intProps?.get(from).toString(),
					onValueChange = {
						val integrer = it.toIntOrNull()
						if (integrer != null) {
							propsContainer.intProps?.set(from, integrer)
							onValuesChanged(from, to)
						}
					},
					modifier = Modifier
						.weight(1.0f),
					label = {
						Text("From")
					}
				)

				Spacer(Modifier
					.width(8.dp))

				OutlinedTextField(
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					value = propsContainer.intProps?.get(to).toString(),
					onValueChange = {
						val integrer = it.toIntOrNull()
						if (integrer != null) {
							propsContainer.intProps?.set(to, integrer)
							onValuesChanged(from, to)
						}
					},
					modifier = Modifier
						.weight(1.0f),
					label = {
						Text("To")
					}
				)
			}

			// STRING

			propsContainer.stringProps != null -> {
				OutlinedTextField(
					value = propsContainer.stringProps?.get(from).toString(),
					onValueChange = {
						propsContainer.stringProps?.set(from, it)
						onValuesChanged(from, to)
					},
					modifier = Modifier
						.weight(1.0f)
				)
			}
		}
	}
}
