package com.lrgs18120163.sicedroid.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lrgs18120163.sicedroid.ViewModels.HomeViewModel
import com.lrgs18120163.sicedroid.data.DefaultAppContainer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lrgs18120163.sicedroid.ViewModels.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, appContainer: DefaultAppContainer) {
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelFactory(appContainer))

    val datosAlumno by viewModel.datosAlumno.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Información del Alumno") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Manejo de estados de carga y error
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Error: $error", color = Color.Red)
                datosAlumno != null -> {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nombre: ${datosAlumno?.nombre}", style = MaterialTheme.typography.headlineSmall)
                            Text(text = "Matrícula: ${datosAlumno?.matricula}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Carrera: ${datosAlumno?.carrera}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Especialidad: ${datosAlumno?.especialidad}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Semestre Actual: ${datosAlumno?.semActual}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
            Button(onClick = { navController.navigate("cargaacademica") }) {
                Text("Ver Carga Academica")
            }
            Button(onClick = { navController.navigate("unidades") }) {
                Text("Ver Calificaciones por Unidad")
            }
            Button(onClick = { navController.navigate("finales") }) {
                Text("Ver Calificaciones Finales")
            }
            Button(onClick = { navController.navigate("kardex") }) {
                Text("Ver Kardex")
            }
        }
    }
}
