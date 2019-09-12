package br.com.felipeacerbi.carpinner.cars.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.felipeacerbi.carpinner.R
import br.com.felipeacerbi.carpinner.cars.viewmodel.CarsViewModel.Action.RequestCars
import br.com.felipeacerbi.carpinner.cars.viewmodel.CarsViewModelImpl
import br.com.felipeacerbi.common.extension.observe
import org.koin.android.viewmodel.ext.android.viewModel

class CarsFragment : Fragment() {

    private val carsViewModel by viewModel<CarsViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()

        observe(carsViewModel.getStateStream()) {
            it.carsList
        }

        carsViewModel.perform(RequestCars)
    }

    private fun showSplashScreen() {
        findNavController().navigate(R.id.action_carsFragment_to_splashFragment)
    }
}