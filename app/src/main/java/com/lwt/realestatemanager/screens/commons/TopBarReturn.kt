package com.lwt.realestatemanager.screens.commons

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lwt.realestatemanager.R

@Composable
fun TopBarReturn(context: Activity, title: String, content: @Composable () -> Unit) {
	Column {
		TopAppBar(
			elevation = 4.dp,
			title = { Text(title) },
			navigationIcon = {
				IconButton(onClick = { context.finish() }) {
					Icon(Icons.Filled.ArrowBack, contentDescription = context.getString(R.string.content_description_return_to_home_screen), tint = Color.White)
				}
			}
		)
		content()
	}
}