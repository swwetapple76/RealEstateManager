package com.lwt.realestatemanager.screens.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@ExperimentalComposeUiApi
@Composable
inline fun <reified E : Enum<E>> OutlinedDropDown(
	currentSelected: Enum<E>,
	modifier: Modifier = Modifier,
	label: String? = null,
	crossinline onValueSelected: (E) -> (Unit),
) {
	var expanded by remember { mutableStateOf(false) }
	var selectedText by remember { mutableStateOf(currentSelected.toString()) }
	var textfieldSize by remember { mutableStateOf(Size.Zero) }

	val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

	Column(modifier.padding(horizontal = 0.dp, vertical = 8.dp)) {
		Box()
		{
			val (focusRequester) = FocusRequester.createRefs()
			val interactionSource = remember { MutableInteractionSource() }

			val labelLambda: @Composable (() -> Unit)? =
				if (label != null) {
					{ Text(label) }
				} else null

			OutlinedTextField(
				label = labelLambda,
				value = selectedText,
				onValueChange = { },
				readOnly = true, trailingIcon = { Image(icon, icon.name) },
				modifier = Modifier
					.fillMaxWidth()
					.padding(2.dp)
					.onGloballyPositioned { coordinates ->
						textfieldSize = coordinates.size.toSize()
					}
					.focusRequester(focusRequester)
			)

			// Handle Click
			Box(modifier = Modifier
				.matchParentSize()
				.clickable(
					onClick = {
						expanded = !expanded
						focusRequester.requestFocus()
					},
					interactionSource = interactionSource,
					indication = null
				))
		}
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
			modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
		) {
			enumValues<E>().asList().forEach { enumValue ->
				DropdownMenuItem(onClick = {
					selectedText = enumValue.toString()
					onValueSelected(enumValue)
					expanded = false
				}) {
					Text(text = enumValue.toString())
				}
			}
		}
	}
}
