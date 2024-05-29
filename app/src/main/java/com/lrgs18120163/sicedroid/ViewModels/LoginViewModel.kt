package com.lrgs18120163.sicedroid.ViewModels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import com.lrgs18120163.sicedroid.model.AccesoAlumno
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val appContainer: DefaultAppContainer) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loginSuccessful = MutableSharedFlow<Boolean>() // Para notificar el éxito del inicio de sesión
    val loginSuccessful: SharedFlow<Boolean> = _loginSuccessful.asSharedFlow()

    val gson = Gson()

    fun login(controlNumber: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val accesoAlumnoJson = appContainer.repositorio.getAcceso(controlNumber, password, "ALUMNO")
                accesoAlumnoJson?.let { json ->
                    val accesoAlumno = gson.fromJson(json, AccesoAlumno::class.java)
                    if (accesoAlumno.acceso) {
                        _loginSuccessful.emit(true) // Notificar éxito
                        appContainer.matricula = accesoAlumno.matricula
                        Toast.makeText(appContainer.applicationContext, "Bienvenido ${accesoAlumno.matricula}, Estatus: ${accesoAlumno.estatus}", Toast.LENGTH_SHORT).show()
                        Log.d("LoginViewModel", "Inicio de sesión exitoso")
                    } else {
                        _error.value = "Número de control o contraseña incorrectos"
                    }
                } ?: run {
                    _error.value = "Error en la solicitud"
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error al iniciar sesión: ${e.message}")
                _error.value = "Error al iniciar sesión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
