package com.lwt.realestatemanager.repository

import android.util.Log
import com.lwt.realestatemanager.model.geocoderapi.GeocoderResponse
import com.lwt.realestatemanager.repository.database.EstateDatabase
import com.lwt.realestatemanager.repository.retrofit.RetrofitInstance
import com.lwt.realestatemanager.repository.retrofit.services.GeocoderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EstateRepository {
	var db: EstateDatabase? = null
	private val geocoderService: GeocoderService = RetrofitInstance.geocoderService
	fun callGeocoderService(address: String, onResponse: (Double, Double) -> (Unit)) {
		val callDetails: Call<GeocoderResponse> = geocoderService.getLocation(address)
		callDetails.enqueue(object : Callback<GeocoderResponse?> {
			override fun onResponse(call: Call<GeocoderResponse?>, response: Response<GeocoderResponse?>) {
				val answer = response.body()
				val list = answer?.results
				var latitude = 0.0
				var longitude = 0.0
				if (list?.size ?: 0 > 0) {
					val location = list?.get(0)?.geometry?.location
					if (location != null) {
						latitude = location.lat!!
						longitude = location.lng!!
					}
				}
				onResponse(latitude, longitude)
			}

			override fun onFailure(call: Call<GeocoderResponse?>, t: Throwable) {
				Log.e("callGeocoderService", "onFailure: Geocoder Call Failed")
			}
		})
	}
}