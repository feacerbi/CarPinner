package br.com.felipeacerbi.carpinner.cars.di

import br.com.felipeacerbi.carpinner.cars.repository.CarsRepository
import br.com.felipeacerbi.carpinner.cars.repository.CarsRepositoryImpl
import br.com.felipeacerbi.carpinner.cars.service.CarsService
import br.com.felipeacerbi.carpinner.cars.viewmodel.CarsViewModelImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val carsModule = module {
    single { provideCarsService(get()) }
    factory<CarsRepository> { CarsRepositoryImpl(get()) }
    viewModel { CarsViewModelImpl(get(), get()) }
}

fun provideCarsService(retrofit: Retrofit): CarsService = retrofit.create(
    CarsService::class.java)