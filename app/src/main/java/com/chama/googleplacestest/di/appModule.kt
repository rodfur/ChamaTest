package com.chama.googleplacestest.di

import com.chama.googleplacestest.data.FetchPlacesUseCase
import com.chama.googleplacestest.data.repository.PlacesRepository
import com.chama.googleplacestest.data.RetrofitGooglePlacesInstance
import com.chama.googleplacestest.ui.viewmodel.PlacesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { PlacesRepository() }
    single { FetchPlacesUseCase(get()) }
    viewModel {
        PlacesViewModel(
            get(),
            get()
        )
    }
    single { RetrofitGooglePlacesInstance }
}