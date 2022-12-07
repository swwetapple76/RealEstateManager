package com.lwt.realestatemanager.screens.commons

import androidx.compose.foundation.Image
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun OutlinedText(value: String, modifier: Modifier = Modifier, leadingIcon: ImageVector? = null, trailingIcon: ImageVector? = null, title: String? = null) {
	val leadingIconLambda: @Composable (() -> Unit)? =
		if (leadingIcon != null) {
			{ Image(leadingIcon, leadingIcon.name) }
		} else null
	val trailingIconLambda: @Composable (() -> Unit)? =
		if (trailingIcon != null) {
			{ Image(trailingIcon, trailingIcon.name) }
		} else null
	val labelLambda: @Composable (() -> Unit)? =
		if (title != null) {
			{ Text(title) }
		} else null

	OutlinedTextField(
		label = labelLambda,
		modifier = modifier,
		leadingIcon = leadingIconLambda,
		trailingIcon = trailingIconLambda,
		value = value,
		onValueChange = {},
		readOnly = true)
}