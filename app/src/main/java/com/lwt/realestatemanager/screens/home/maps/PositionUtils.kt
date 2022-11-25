package com.lwt.realestatemanager.screens.home.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

val ErrorLoc: Location = Location("").apply {
	latitude = 0.0
	longitude = 0.0
}

@SuppressLint("MissingPermission") // Checked properly before
fun getMyLocation(context: Context): Location {
	val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
	val criteria = Criteria()
	if (ActivityCompat.checkSelfPermission(context,
			Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
			Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
	) {
		return ErrorLoc
	}
	return locationManager.getBestProvider(criteria, false)?.let { locationManager.getLastKnownLocation(it) } ?: ErrorLoc
}