package com.weatherapplication.view.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.weatherapplication.viewmodel.WeatherViewModel

@Preview
@Composable
fun tesyt() {
    //DetailedInfo(viewModel)
}

@Composable
fun WeatherScreenComponents(
    viewModel: WeatherViewModel,
    onSearchButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchButton { onSearchButtonClick() }
        viewModel.weatherData.value.name?.let { CityNameTitle(it) }
        viewModel.weatherData.value.main?.getTemp()?.let { CurrentTemperature(it) }
        CurrentCondition(viewModel.weatherData.value.weather?.get(0)?.description)
        DetailedInfo(viewModel)
    }
}

@Composable
fun SearchButton(
    onSearchButtonClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(4.dp),
        onClick = { onSearchButtonClick() }
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.Search,
            contentDescription = "Search city button",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CityNameTitle(name: String) {
    Text(
        text = name,
        modifier = Modifier
            .padding(top = 12.dp, start = 4.dp, end = 4.dp, bottom = 16.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CurrentTemperature(temperature: String) {
    Text(
        text = "$temperature°",
        modifier = Modifier
            .padding(top = 16.dp, start = 4.dp, end = 4.dp)
            .fillMaxWidth(),
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 148.sp
    )
}

@Composable
fun CurrentCondition(description: String?) {
    if (description != null) {
        Text(
            text = description.uppercase(),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun DetailedInfo(viewModel: WeatherViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        viewModel.weatherData.value.weather?.get(0)?.icon?.let { CurrentConditionIcon(it) }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            viewModel.weatherData.value.main?.let {
                InfoTextElement("Feels like ${it.getFeelsLikeTemp()}°")
                InfoTextElement("High ${it.getMaxTemp()}° / Low ${it.getMinTemp()}°")
                InfoTextElement("Humidity ${it.humidity}%")
            }
        }
    }
}

@Composable
fun CurrentConditionIcon(conditionId: String) {
    AsyncImage(
        model = "https://openweathermap.org/img/wn/$conditionId@2x.png",
        contentDescription = null,
        modifier = Modifier.width(150.dp).height(150.dp)
    )
}

@Composable
fun InfoTextElement(info: String) {
    Text(
        text = info,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        color = Color.White,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}