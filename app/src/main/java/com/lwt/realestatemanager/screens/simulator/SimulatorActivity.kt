package com.lwt.realestatemanager.screens.simulator

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lwt.realestatemanager.screens.simulator.navigation.SetupNavHostController
import com.lwt.realestatemanager.screens.simulator.utils.Constants
import com.lwt.realestatemanager.ui.theme.RealEstateManagerTheme


class SimulatorActivity : ComponentActivity() {
	private lateinit var firebaseAnalytics: FirebaseAnalytics

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		firebaseAnalytics = Firebase.analytics

		setContent {
			val navController = rememberNavController()
			RealEstateManagerTheme() {
				SetupNavHostController(navController)

				if (savedInstanceState == null){
					navController.navigate(Constants.DEST_INITIAL)
				}
			}
		}
	}

	/**Hide keyboard on outside click*/
	override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
		if (currentFocus != null){
			val imm: InputMethodManager =
				getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
		}
		return super.dispatchTouchEvent(ev)
	}

}