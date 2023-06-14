package com.weatherapplication.model.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(

	@Json(name="list")
	val forecasts: List<ListItem?>? = null
)

@JsonClass(generateAdapter = true)
data class ListItem(

	@Json(name="dt")
	val dt: Int? = null,

	@Json(name="pop")
	val pop: Any? = null,

	@Json(name="visibility")
	val visibility: Int? = null,

	@Json(name="dt_txt")
	val dtTxt: String? = null,

	@Json(name="weather")
	val weather: List<WeatherItem?>? = null,

	@Json(name="main")
	val main: Main? = null,

	@Json(name="clouds")
	val clouds: Clouds? = null,

	@Json(name="wind")
	val wind: Wind? = null,

	@Json(name="rain")
	val rain: Rain? = null
)