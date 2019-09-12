package br.com.felipeacerbi.carpinner.cars.viewmodel

import androidx.lifecycle.MutableLiveData
import br.com.felipeacerbi.carpinner.cars.viewstate.CarsViewState

interface CarsViewModel {

    fun getStateStream(): MutableLiveData<CarsViewState>
    fun perform(action: Action)

    sealed class Action {
        object RequestCars : Action()
    }
}