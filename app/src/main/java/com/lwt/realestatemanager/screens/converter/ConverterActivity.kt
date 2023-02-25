package com.lwt.realestatemanager.screens.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lwt.realestatemanager.screens.commons.TopBarReturn
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme

class ConverterActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {
				TopBarReturn(this, "Converter Tools") {
					Column(Modifier.fillMaxSize(),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally) {
						Text("Dollar / Euro", style = MaterialTheme.typography.h3,
							textAlign = TextAlign.Center)
						val configuration = LocalConfiguration.current
						if (configuration.screenWidthDp > 450)
							Row(modifier = Modifier.padding(8.dp)) {
								ConverterFields()
							}
						else
							Column(modifier = Modifier.padding(8.dp)) {
								ConverterFields()
							}
					}
				}
			}
		}
	}
}