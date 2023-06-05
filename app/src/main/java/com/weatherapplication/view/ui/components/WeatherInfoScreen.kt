package com.weatherapplication.view.ui.components

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weatherapplication.viewmodel.WeatherViewModel

@Composable
fun WeatherScreenComponents(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {

    }
}


@Composable
fun CityNameTitle(name: String) {
    Text(
        text = name,
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        color = MaterialTheme.colorScheme.
    )
}