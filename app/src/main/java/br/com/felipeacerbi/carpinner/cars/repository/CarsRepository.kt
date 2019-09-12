package br.com.felipeacerbi.carpinner.cars.repository

import br.com.felipeacerbi.carpinner.cars.service.model.Car

interface CarsRepository {
    suspend fun requestCars(): List<Car>
}