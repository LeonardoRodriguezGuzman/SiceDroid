package com.lrgs18120163.sicedroid.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import com.lrgs18120163.sicedroid.model.CalificacionesFinales
import com.lrgs18120163.sicedroid.model.CalificacionesUnidades
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CalificacionesFinalesViewModel(private val appContainer: DefaultAppContainer) : ViewModel() {
    private val _calificacionesFinales = MutableStateFlow<List<CalificacionesFinales>>(emptyList())
    val calificacionesFinales: StateFlow<List<CalificacionesFinales>> = _calificacionesFinales

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val calificacionesFinalesJson = appContainer.repositorio.getAllCalifFinalByAlumnos(0)
                calificacionesFinalesJson?.let { json ->
                    _calificacionesFinales.value = Json.decodeFromString<List<CalificacionesFinales>>(json)
                } ?: run {
                    _error.value = "Error: No se pudieron obtener las calificaciones finales"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar las calificaciones finales: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
