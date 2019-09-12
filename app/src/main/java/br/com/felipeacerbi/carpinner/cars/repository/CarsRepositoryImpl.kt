package br.com.felipeacerbi.carpinner.cars.repository

import br.com.felipeacerbi.carpinner.cars.service.CarsService
import br.com.felipeacerbi.carpinner.cars.service.model.Car

class CarsRepositoryImpl(
    private val carsService: CarsService
) : CarsRepository {

    override suspend fun requestCars(): List<Car> = carsService.getCars()
}