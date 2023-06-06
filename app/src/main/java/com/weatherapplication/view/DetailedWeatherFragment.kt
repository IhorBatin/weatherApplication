package com.weatherapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weatherapplication.R
import com.weatherapplication.util.USE_USER_ENTRY
import com.weatherapplication.view.ui.components.WeatherScreenComponents
import com.weatherapplication.view.ui.theme.WeatherApplicationTheme
import com.weatherapplication.viewmodel.WeatherViewModel

class DetailedWeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val useUserEntry = arguments?.getBoolean(USE_USER_ENTRY) ?: false

        if (useUserEntry) {
            viewModel.geocodeByCityName()
        }

        return ComposeView(requireContext()).apply {
            setContent {
                WeatherApplicationTheme {
                    WeatherScreenComponents(viewModel) {
                        findNavController().navigate(R.id.to_citySelectorFragment)
                    }
                }
            }
        }
    }
}