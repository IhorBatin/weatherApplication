package com.weatherapplication.model.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class GeocodingResponse(

	val geocodingResponse: List<GeocodingResponseItem?>? = null
)

@JsonClass(generateAdapter = true)
data class GeocodingResponseItem(

	@Json(name="country")
	val country: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="lon")
	val lon: Any? = null,

	@Json(name="state")
	val state: String? = null,

	@Json(name="lat")
	val lat: Any? = null
)
