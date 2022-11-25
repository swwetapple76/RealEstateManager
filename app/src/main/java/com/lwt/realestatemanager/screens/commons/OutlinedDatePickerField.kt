package com.lwt.realestatemanager.screens.commons

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.utils.ExtensionUtils.estateFormat
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun OutlinedDatePickerButton(
	modifier: Modifier = Modifier,
	title: String? = null,
	leadingIcon: ImageVector? = null,
	trailingIcon: ImageVector? = null,
	onValueChange: (Date) -> Unit,
) {
	var showDialog by remember { mutableStateOf(false) }
	var selectedDate by remember { mutableStateOf(Date()) }

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

	if (showDialog) {
		val context = LocalContext.current
		val mYear: Int
		val mMonth: Int
		val mDay: Int
		val now = Calendar.getInstance()
		mYear = now.get(Calendar.YEAR)
		mMonth = now.get(Calendar.MONTH)
		mDay = now.get(Calendar.DAY_OF_MONTH)
		now.time = Date()
		val dialog = DatePickerDialog(
			context,
			{ _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
				val cal = Calendar.getInstance()
				cal.set(year, month, dayOfMonth)
				onValueChange(cal.time)
				showDialog = false
				selectedDate = cal.time
			}, mYear, mMonth, mDay
		)
		dialog.setOnDismissListener { showDialog = false }
		dialog.setOnCancelListener { showDialog = false }
		dialog.show()
	}

	Box(modifier)
	{
		val (focusRequester) = FocusRequester.createRefs()
		val interactionSource = remember { MutableInteractionSource() }
		OutlinedTextField(
			label = labelLambda,
			textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
			value = selectedDate.estateFormat(),
			onValueChange = { },
			readOnly = true,
			trailingIcon = trailingIconLambda,
			leadingIcon = leadingIconLambda,
			modifier = Modifier
				.fillMaxWidth()
				.padding(2.dp)
				.focusRequester(focusRequester)
		)
		// Handle Click
		Box(modifier = Modifier
			.matchParentSize()
			.clickable(
				onClick = {
					showDialog = true
					focusRequester.requestFocus()
				},
				interactionSource = interactionSource,
				indication = null
			))
	}
}