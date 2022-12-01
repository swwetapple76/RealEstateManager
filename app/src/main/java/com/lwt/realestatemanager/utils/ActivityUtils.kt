package com.lwt.realestatemanager.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.lwt.realestatemanager.model.Photo
import com.lwt.realestatemanager.screens.converter.ConverterActivity
import com.lwt.realestatemanager.screens.photoviewer.PhotoViewerActivity
import com.lwt.realestatemanager.screens.simulator.SimulatorActivity

object ActivityUtils {

	// Easy Activity Open

	@ExperimentalCoilApi
	fun openPhotoViewerActivity(context: Context, photo: Photo) {
		val intent = Intent(context, PhotoViewerActivity::class.java).apply {
			putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	fun openConverterActivity(context: Context) {
		val intent = Intent(context, ConverterActivity::class.java).apply {
			//putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	fun openSimulatorActivity(context: Context) {
		val intent = Intent(context, SimulatorActivity::class.java).apply {
			//putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}
}