package com.weatherapplication.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weatherapplication.viewmodel.WeatherViewModel

@Composable
fun SelectCityBar(viewModel: WeatherViewModel) {
    AutoCompleteTextView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        query = viewModel.selectedCityName.value,
        queryLabel = "Choose City",
        predictions = viewModel.possibleCityOptions,
        onQueryChanged = {
            viewModel.selectedCityName.value = it
            viewModel.updateLikeCityNames(it)
        },
        onDoneActionClick = {
            //viewModel.setSelectedCityName(text)
        },
        onItemClick = {
            viewModel.onItemClick(it)
            viewModel.selectedCityName.value = it.fullCityName
        },
        onClearClick = {
            viewModel.onClearClick()
        }
    )
}

@Composable
fun ErrorMessage(showError: Boolean) {
    if (showError) {
        Text(
            text = "Please select valid US city.",
            color = Color.Red,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@Composable
fun NextButton(
    onNextClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = { onNextClick() },
        enabled = enabled,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Check weather")
    }

}