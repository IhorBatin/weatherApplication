package com.weatherapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weatherapplication.R
import com.weatherapplication.util.SP_LAST_CITY_NAME_KEY
import com.weatherapplication.util.USE_USER_ENTRY
import com.weatherapplication.view.ui.components.CloseButton
import com.weatherapplication.view.ui.components.ErrorMessage
import com.weatherapplication.view.ui.components.NextButton
import com.weatherapplication.view.ui.components.SelectCityBar
import com.weatherapplication.view.ui.theme.WeatherApplicationTheme
import com.weatherapplication.viewmodel.WeatherViewModel

class CitySelectorFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getPreviousCityNameIfSaved()

        return ComposeView(requireContext()).apply {
            setContent {
                WeatherApplicationTheme {
                    Column() {
                        CloseButton(onCloseClick = {
                                findNavController().navigate(R.id.to_detailedWeatherFragment)
                            }
                        )
                        SelectCityBar(viewModel)
                        NextButton(onNextClick = {
                                viewModel.checkCityExists()
                                checkAndNavigateToWeatherFragment()
                                saveCityNameToSp()
                            },
                            viewModel.selectedCityName.value.isNotEmpty()
                        )
                        ErrorMessage(viewModel.shouldShowError.value)
                    }
                }
            }
        }
    }

    private fun saveCityNameToSp() {
        with(requireActivity().getPreferences(Context.MODE_PRIVATE).edit()) {
            putString(SP_LAST_CITY_NAME_KEY, viewModel.selectedCityName.value)
            apply()
        }
    }

    private fun getPreviousCityNameIfSaved() {
        val cityNameFromPrevSession = requireActivity()
            .getPreferences(Context.MODE_PRIVATE)
            .getString(SP_LAST_CITY_NAME_KEY, "") ?: ""

        viewModel.setSelectedCityName(cityNameFromPrevSession)
    }

    private fun checkAndNavigateToWeatherFragment() {
        if (viewModel.doesCityExist.value)
            findNavController().navigate(
                R.id.to_detailedWeatherFragment, bundleOf(USE_USER_ENTRY to true)
            )
    }
}