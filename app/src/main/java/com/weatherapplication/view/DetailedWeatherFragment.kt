package com.weatherapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weatherapplication.R
import com.weatherapplication.util.SP_UNIT_SWITCH_STATE_KEY
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
        getPreviousUnitSwitchSate()
        val useUserEntry = arguments?.getBoolean(USE_USER_ENTRY) ?: false

        if (useUserEntry) {
            viewModel.geocodeByCityName()
        }

        return ComposeView(requireContext()).apply {
            setContent {
                WeatherApplicationTheme {
                    WeatherScreenComponents(
                        viewModel = viewModel,
                        onSearchButtonClick = {
                            findNavController().navigate(R.id.to_citySelectorFragment)
                        },
                        onUnitsChanged = {
                            updateAndSaveUserSelectedUnits(it)
                        }
                    )
                }
            }
        }
    }

    private fun getPreviousUnitSwitchSate() {
        val unitsSwitchState = requireActivity()
            .getPreferences(Context.MODE_PRIVATE)
            .getBoolean(SP_UNIT_SWITCH_STATE_KEY, true)

        viewModel.updateUnits(unitsSwitchState)
    }

    private fun updateAndSaveUserSelectedUnits(switchState: Boolean) {
        viewModel.updateUnits(switchState)
        viewModel.updateWeatherData()

        // Saving state to SP
        with(requireActivity().getPreferences(Context.MODE_PRIVATE).edit()) {
            putBoolean(SP_UNIT_SWITCH_STATE_KEY, viewModel.unitsSwitchState.value)
            apply()
        }
    }
}