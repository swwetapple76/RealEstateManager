package com.lwt.realestatemanager.repository.retrofit.services

import com.lwt.realestatemanager.BuildConfig
import com.lwt.realestatemanager.model.geocoderapi.GeocoderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderService {
	@GET("api/geocode/json?key=" + BuildConfig.MAPS_API_KEY)
	fun getLocation(
		@Query("address") address: String,
	): Call<GeocoderResponse>
}