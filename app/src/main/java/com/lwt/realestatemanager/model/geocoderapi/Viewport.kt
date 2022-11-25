package com.lwt.realestatemanager.model.geocoderapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Viewport {
	@SerializedName("northeast")
	@Expose
	var northeast: Northeast? = null

	@SerializedName("southwest")
	@Expose
	var southwest: Southwest? = null
}