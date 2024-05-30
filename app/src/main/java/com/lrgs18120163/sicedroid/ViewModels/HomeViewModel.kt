package com.lrgs18120163.sicedroid.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.lrgs18120163.sicedroid.data.AppContainer
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import com.lrgs18120163.sicedroid.model.DatosAlumno
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat

class HomeViewModel(private val appContainer: DefaultAppContainer) : ViewModel() {
    val gson = Gson()
    private val _datosAlumno = MutableStateFlow<DatosAlumno?>(null)
    val datosAlumno: StateFlow<DatosAlumno?> = _datosAlumno

    private val _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val datos = appContainer.repositorio.getAlumnoAcademicoWithLineamiento()
                val carga = appContainer.repositorio.getCargaAcademicaByAlumno()
                val kardex = appContainer.repositorio.getAllKardexConPromedioByAlumno(1)
                Log.d("carga","$carga")
                Log.d("kardex","$kardex")
                datos?.let { json ->
                    val dAlumno = gson.fromJson(json, DatosAlumno::class.java)
                    val sdf = SimpleDateFormat("dd/MM/yyyy|HH:mm")
                    val fecha = sdf.parse(dAlumno.fechaReins)
                    _datosAlumno.value = dAlumno.copy(fechaReins = fecha.toString()) // Actualizar el valor
                } ?: run {
                    _error.value = "Error al cargar datos del alumno"
                }
            } catch (e: Exception){
                Log.e("HomeViewModel","Error al cargar datos del alumno: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}