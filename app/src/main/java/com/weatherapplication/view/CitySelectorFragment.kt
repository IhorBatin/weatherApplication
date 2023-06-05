package com.weatherapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weatherapplication.R
import com.weatherapplication.view.ui.components.ErrorMessage
import com.weatherapplication.view.ui.components.NextButton
import com.weatherapplication.view.ui.components.SelectCityBar
import com.weatherapplication.view.ui.theme.WeatherApplicationTheme
import com.weatherapplication.viewmodel.WeatherViewModel

class CitySelectorFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WeatherApplicationTheme {
                    Column() {
                        SelectCityBar(viewModel)
                        NextButton(onNextClick = {
                                viewModel.checkCityExists()
                                checkAndNavigateToWeatherFragment()
                            },
                            viewModel.selectedCityName.value.isNotEmpty()
                        )
                        ErrorMessage(viewModel.shouldShowError.value)
                    }
                }
            }
        }
    }

    private fun checkAndNavigateToWeatherFragment() {
        if (viewModel.doesCityExist.value)
            findNavController().navigate(R.id.to_detailedWeatherFragment)
    }
}