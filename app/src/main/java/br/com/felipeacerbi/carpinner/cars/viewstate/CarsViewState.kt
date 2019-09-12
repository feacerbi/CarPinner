package br.com.felipeacerbi.carpinner.cars.viewstate

import br.com.felipeacerbi.carpinner.cars.service.model.Car
import br.com.felipeacerbi.common.viewstate.ViewState

data class CarsViewState(
    var showLoading: Boolean = false,
    var showError: Boolean = false,
    var errorMessage: String = "",
    var carsList: List<Car> = listOf()
) : ViewState