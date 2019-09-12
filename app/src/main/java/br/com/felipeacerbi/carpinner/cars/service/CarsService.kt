package br.com.felipeacerbi.carpinner.cars.service

import br.com.felipeacerbi.carpinner.cars.service.model.Car
import retrofit2.http.GET

interface CarsService {
    @GET("cars")
    suspend fun getCars(): List<Car>
}