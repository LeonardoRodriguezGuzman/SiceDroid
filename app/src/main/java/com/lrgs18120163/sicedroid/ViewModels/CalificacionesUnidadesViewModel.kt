package com.lrgs18120163.sicedroid.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import com.lrgs18120163.sicedroid.model.CalificacionesUnidades
import com.lrgs18120163.sicedroid.model.CargaAcademica
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CalificacionesUnidadesViewModel(private val appContainer: DefaultAppContainer) : ViewModel() {
    private val _calificaciones = MutableStateFlow<List<CalificacionesUnidades>>(emptyList())
    val calificaciones: StateFlow<List<CalificacionesUnidades>> = _calificaciones

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val calificacionesJson = appContainer.repositorio.getCalifUnidadesByAlumno()
                calificacionesJson?.let { json ->
                    _calificaciones.value = Json.decodeFromString<List<CalificacionesUnidades>>(json)
                } ?: run {
                    _error.value = "Error: No se pudieron obtener las calificaciones"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar las calificaciones: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

