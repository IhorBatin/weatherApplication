package com.weatherapplication.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.weatherapplication.util.DATE_PATTERN
import com.weatherapplication.util.TIMEZONE_UTC
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(

	@Json(name="list")
	val forecasts: List<ForecastItem?>? = null
)

@JsonClass(generateAdapter = true)
data class ForecastItem(

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
) {
	private fun getDate(): LocalDateTime? {
		// Time for forecast is converted from UTC to local device timezone
		val apiFormat = SimpleDateFormat(DATE_PATTERN)
		apiFormat.timeZone = TimeZone.getTimeZone(TIMEZONE_UTC)
		return dtTxt?.let {
			apiFormat.parse(it)?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
		}
	}

	fun getDay(): String = getDate()?.dayOfWeek.toString().take(3)

	fun getHour(): String = "${getDate()?.hour.toString()}:00"
}