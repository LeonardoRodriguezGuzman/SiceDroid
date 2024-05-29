package com.lrgs18120163.sicedroid.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import com.lrgs18120163.sicedroid.model.CargaAcademica
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CargaAcademicaViewModel(private val appContainer: DefaultAppContainer) : ViewModel() {
    private val _horario = MutableStateFlow<List<CargaAcademica>>(emptyList())
    val horario: StateFlow<List<CargaAcademica>> = _horario

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val horarioJson = appContainer.repositorio.getCargaAcademicaByAlumno()
                horarioJson?.let { json ->
                    _horario.value = Json.decodeFromString<List<CargaAcademica>>(json)
                } ?: run {
                    _error.value = "Error: No se pudo obtener el horario"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el horario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}