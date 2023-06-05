package com.weatherapplication.model.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class CityCoordinates(

	@Json(name="lon")
	val lon: String,

	@Json(name="lat")
	val lat: String
)
