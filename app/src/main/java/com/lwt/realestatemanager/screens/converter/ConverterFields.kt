package com.lwt.realestatemanager.screens.converter

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lwt.realestatemanager.utils.Utils

@Composable
fun ConverterFields() {

	var euro by remember { mutableStateOf("") }
	var dollar by remember { mutableStateOf("") }

	OutlinedTextField(
		value = euro,
		onValueChange = {
			euro = it
			val result = it.toIntOrNull()
			dollar = if (result != null)
				Utils.convertEuroToDollar(result).toString()
			else
				"Incorrect Euro Value"
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Number
		),
		label = { Text("Euro") },
		modifier = Modifier
			.onFocusChanged {
				if (it.isFocused)
					euro = ""
			}
			.padding(8.dp)
	)
	OutlinedTextField(
		value = dollar,
		onValueChange = {
			dollar = it
			val result = it.toIntOrNull()
			euro = if (result != null)
				Utils.convertDollarToEuro(result).toString()
			else
				"Incorrect Dollar Value"
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Number
		),
		label = { Text("Dollar") },
		modifier = Modifier
			.onFocusChanged {
				if (it.isFocused)
					dollar = ""
			}
			.padding(8.dp)
	)
}