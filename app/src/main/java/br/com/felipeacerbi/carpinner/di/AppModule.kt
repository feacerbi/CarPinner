package br.com.felipeacerbi.carpinner.di

import br.com.felipeacerbi.common.dispatcher.CoroutineDispatchers
import br.com.felipeacerbi.common.dispatcher.CoroutineDispatchersImpl
import org.koin.dsl.module

val appModule = module {
    single<CoroutineDispatchers> { CoroutineDispatchersImpl() }
}