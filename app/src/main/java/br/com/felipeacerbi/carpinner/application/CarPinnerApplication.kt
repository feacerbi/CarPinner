package br.com.felipeacerbi.carpinner.application

import android.app.Application
import br.com.felipeacerbi.carpinner.cars.di.carsModule
import br.com.felipeacerbi.carpinner.di.appModule
import br.com.felipeacerbi.carpinner.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CarPinnerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CarPinnerApplication)
            modules(listOf(appModule, networkModule, carsModule))
        }
    }
}