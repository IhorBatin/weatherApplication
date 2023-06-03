package com.weatherapplication.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.weatherapplication.view.ui.components.AutoCompleteTextView
import com.weatherapplication.view.ui.theme.WeatherApplicationTheme
import com.weatherapplication.viewmodel.WeatherViewModel

class MainWeatherActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WeatherApplicationTheme {
                var text by remember { mutableStateOf("") }
                AutoCompleteTextView(
                    modifier = Modifier.fillMaxWidth(),
                    query = text,
                    queryLabel = "Choose City",
                    onQueryChanged = {
                        text = it
                        viewModel.updateLikeCityNames(text)
                    },
                    onDoneActionClick = {
                                       // text =
                    },
                    onClearClick = {
                                   text = ""
                    },
                    predictions = viewModel.possibleCityOptions
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherApplicationTheme {
        Greeting("Android")
    }
}