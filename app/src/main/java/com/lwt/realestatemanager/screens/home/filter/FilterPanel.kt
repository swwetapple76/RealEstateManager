package com.lwt.realestatemanager.screens.home.filter

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.screens.commons.OutlinedDropDown
import com.lwt.realestatemanager.screens.commons.OutlinedFieldFromTo
import com.lwt.realestatemanager.screens.home.HomeViewModel

@Suppress("SelfAssignment")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EtateListFilter(top: Dp, viewmodel: HomeViewModel, closeFilterView: () -> (Unit)) {
	Column(Modifier
		.padding(top = top, start = 8.dp, end = 8.dp, bottom = 8.dp)
		.fillMaxHeight()
		.fillMaxWidth()
		.verticalScroll(rememberScrollState())
	) {

		// Remembers

		var estateFrom by remember {
			mutableStateOf(viewmodel.getFilterSetting().from,
				policy = neverEqualPolicy())
		}

		var estateTo by remember {
			mutableStateOf(viewmodel.getFilterSetting().to,
				policy = neverEqualPolicy())
		}

		var mapOfProps by remember {
			mutableStateOf(viewmodel.getFilterSetting().mapOfProps,
				policy = neverEqualPolicy())
		}

		var checkboxType by remember {
			mutableStateOf(viewmodel.getFilterSetting().type,
				policy = neverEqualPolicy())
		}
		var checkboxStatus by remember {
			mutableStateOf(viewmodel.getFilterSetting().status,
				policy = neverEqualPolicy())
		}


		// Buttons

		Row(Modifier.fillMaxWidth())
		{
			Button(onClick = {
				val copy = FilterSettings.Default.copy()

				// Reset map
				mapOfProps = copy.mapOfProps

				// Reset Estates
				estateTo = copy.to
				estateFrom = copy.from

				// Reset Enum Checkbox
				checkboxType = copy.type
				checkboxStatus = copy.status

				viewmodel.setFilterSetting(copy)
				closeFilterView()
			},
				Modifier
					.weight(1.0f)) {
				Text("Reset")
			}
			Spacer(Modifier.width(8.dp))
			Button(onClick = {
				viewmodel.setFilterSetting(FilterSettings(
					to = estateTo,
					from = estateFrom,
					enabled = true,
					type = checkboxType,
					status = checkboxStatus,
					mapOfProps = mapOfProps
				))
				closeFilterView()
			},
				Modifier
					.weight(1.0f)) {
				Text("Apply")
			}
		}

		Row(Modifier.padding(top = 8.dp)) {
			Checkbox(checked = checkboxType, onCheckedChange = { checkboxType = it })
			Text(text = "Type",
				fontWeight = FontWeight.Bold,
				color = Color(0xffa0a0a0),
				modifier = Modifier.padding(start = 8.dp))
		}
		OutlinedDropDown(currentSelected = estateFrom.type, onValueSelected = {
			estateFrom.type = it
			estateFrom = estateFrom
		})

		Row(Modifier.padding(top = 8.dp)) {
			Checkbox(checked = checkboxStatus, onCheckedChange = { checkboxStatus = it })
			Text(text = "Status",
				fontWeight = FontWeight.Bold,
				color = Color(0xffa0a0a0),
				modifier = Modifier.padding(start = 8.dp))
		}
		OutlinedDropDown(label = "Status", currentSelected = estateFrom.status, onValueSelected = {
			estateFrom.status = it
			estateFrom = estateFrom
		})

		// -------------------
		// Props Spawn
		// -------------------
		mapOfProps.keys.forEach { field ->
			Row(Modifier.padding(top = 8.dp)) {
				Checkbox(checked = mapOfProps[field] ?: false, onCheckedChange = { mapOfProps[field] = it; mapOfProps = mapOfProps })
				Text(
					text = field.getName(),
					fontWeight = FontWeight.Bold,
					color = Color(0xffa0a0a0),
					modifier = Modifier.padding(start = 8.dp)
				)
			}
			OutlinedFieldFromTo(propsContainer = field, from = estateFrom, to = estateTo, onValuesChanged = { from, to -> estateFrom = from; estateTo = to })
		}
	}
}