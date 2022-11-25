package com.lwt.realestatemanager.model.geocoderapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeocoderResponse {
	@SerializedName("results")
	@Expose
	var results: List<Result>? = null

	@SerializedName("status")
	@Expose
	var status: String? = null
}