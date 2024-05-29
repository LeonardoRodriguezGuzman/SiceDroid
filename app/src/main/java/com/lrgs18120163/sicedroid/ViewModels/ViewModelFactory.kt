package com.lrgs18120163.sicedroid.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lrgs18120163.sicedroid.data.DefaultAppContainer

class AppViewModelFactory(private val appContainer: DefaultAppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(appContainer)
            HomeViewModel::class.java -> HomeViewModel(appContainer)
            CargaAcademicaViewModel::class.java -> CargaAcademicaViewModel(appContainer)
            // Agrega aquí otros ViewModels que utilicen AppContainer
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}