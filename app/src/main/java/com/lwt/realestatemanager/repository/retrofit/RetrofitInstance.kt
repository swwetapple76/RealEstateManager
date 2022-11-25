package com.lwt.realestatemanager.repository.retrofit

import com.lwt.realestatemanager.repository.retrofit.services.GeocoderService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
	private const val BASE_URL = "https://maps.googleapis.com/maps/"
	private val retrofit: Retrofit by lazy {
		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()
	}
	val geocoderService: GeocoderService by lazy {
		retrofit.create(GeocoderService::class.java)
	}
}