package com.weatherapplication.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
            viewModel.checkCityExists()
        },
        onItemClick = {
            viewModel.onCityListItemClick(it)
            viewModel.selectedCityName.value = it.fullCityName
            viewModel.checkCityExists()
        },
        onClearClick = {
            viewModel.clearCityOptions()
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

@Composable
fun CloseButton(
    onCloseClick: () -> Unit
) {
    IconButton(
        onClick = { onCloseClick() },
        modifier = Modifier
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Default.Close,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Close city selector screen"
        )
    }
}