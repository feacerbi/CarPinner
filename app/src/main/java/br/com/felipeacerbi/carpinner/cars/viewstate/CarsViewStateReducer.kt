package br.com.felipeacerbi.carpinner.cars.viewstate

import br.com.felipeacerbi.carpinner.cars.service.model.Car
import br.com.felipeacerbi.common.viewstate.ViewStateReducer

sealed class CarsViewStateReducer : ViewStateReducer<CarsViewState> {
    object ShowLoading : CarsViewStateReducer() {
        override val updateView: CarsViewState.() -> Unit = {
            showLoading = true
        }
    }
    data class ShowError(val exception: Exception) : CarsViewStateReducer() {
        override val updateView: CarsViewState.() -> Unit = {
            showLoading = false
            showError = true
            errorMessage = exception.message ?: "Unknown error"
        }
    }
    data class ShowData(val list: List<Car>) : CarsViewStateReducer() {
        override val updateView: CarsViewState.() -> Unit = {
            showLoading = false
            carsList = list
            moveCamera = true
        }
    }
}