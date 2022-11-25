package com.lwt.realestatemanager.screens.simulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lwt.realestatemanager.screens.commons.TopBarReturn
import com.lwt.realestatemanager.screens.editestate.OutlinedTextFieldLazy
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme
import kotlin.math.pow
import kotlin.math.roundToInt

class SimulatorActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {
				TopBarReturn(this, "Simulator") {
					Box(Modifier.fillMaxSize())
					{
						Row(Modifier
							.align(Alignment.Center)
							.padding(horizontal = 32.dp)
						) {
							var initial by remember { mutableStateOf(5000) }
							var loan by remember { mutableStateOf(150000) }
							var years by remember { mutableStateOf(10) }
							var rate by remember { mutableStateOf(1.5f) }
							var monthly by remember { mutableStateOf(1302) }

							val updateValues = {
								val c = loan - initial
								val nbMonth = 12 * years
								val rtm = rate / 100 / 12
								val a = c * rtm
								val b = 1 - (1 + rtm).pow(-nbMonth)

								monthly = (a / b).roundToInt()
							}

							Column(Modifier.padding(16.dp)) {
								OutlinedTextFieldLazy(title = "Start Money", keyboardType = KeyboardType.Number, value = initial.toString()) {
									val integrer = it.toIntOrNull()
									if (integrer != null) {
										initial = integrer
										updateValues()
									}
								}
								OutlinedTextFieldLazy(title = "Price", keyboardType = KeyboardType.Number, value = loan.toString()) {
									val integrer = it.toIntOrNull()
									if (integrer != null) {
										loan = integrer
										updateValues()
									}
								}
								OutlinedTextFieldLazy(title = "Years", keyboardType = KeyboardType.Number, value = years.toString()) {
									val integrer = it.toIntOrNull()
									if (integrer != null) {
										years = integrer
										updateValues()
									}
								}
								OutlinedTextFieldLazy(title = "Rate", keyboardType = KeyboardType.Number, value = rate.toString()) {
									val float = it.toFloatOrNull()
									if (float != null) {
										rate = float
										updateValues()
									}
								}
							}
							Column(Modifier.padding(16.dp)) {
								Text(text = "Your monthly payment will be", style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
								Text(monthly.toString(), style = MaterialTheme.typography.h1)
							}
						}
					}
				}
			}
		}
	}
}