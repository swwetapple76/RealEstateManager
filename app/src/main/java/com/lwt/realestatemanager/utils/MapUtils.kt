package com.lwt.realestatemanager.utils

import com.lwt.realestatemanager.BuildConfig

object MapUtils {
	fun formatApiRequestStaticMap(
		width: Int = 400,
		height: Int = 400,
		zoom: Int = 15,
		localisation: String = "-74.005157,40.710785",
		apiKey: String = BuildConfig.MAPS_API_KEY,
	): String {
		return "https://maps.googleapis.com/maps/api/staticmap?" +
				"center=$localisation" +
				"&size=${width}x${height}" +
				"&zoom=$zoom" +
				"&markers=color:red%7C$localisation" +
				"&key=$apiKey"
	}
}