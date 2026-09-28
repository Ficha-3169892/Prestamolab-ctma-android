package com.ctma.prestamolab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ctma.prestamolab.data.model.Equipo
import com.ctma.prestamolab.data.model.Solicitud
import com.ctma.prestamolab.ui.state.PrestamoUiState
import com.ctma.prestamolab.ui.theme.PrestamoLabTheme
import com.ctma.prestamolab.ui.viewmodel.PrestamoViewModel

@Composable
fun HomeScreen(
    viewModel: PrestamoViewModel,
    userRole: String = "APRENDIZ",
    userEmail: String = "aprendiz@formacion.ctma",
    onLogout: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiState = state) {
        is PrestamoUiState.Cargando -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PrestamoUiState.Vacio -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay equipos ni herramientas disponibles.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        is PrestamoUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        is PrestamoUiState.Exito -> {
            HomeScreenContent(
                equipos = uiState.equipos,
                solicitudes = uiState.solicitudes,
                mensajeError = uiState.mensajeErrorFormulario,
                userRole = userRole,
                userEmail = userEmail,
                onLogout = onLogout,
                onSolicitarPrestamo = { equipo, ambiente, proposito, hrs, evidencia ->
                    viewModel.solicitarPrestamo(equipo, ambiente, proposito, hrs, evidencia)
                },
                onCancelarSolicitud = { solicitud ->
                    viewModel.cancelarSolicitud(solicitud)
                },
                onMarcarEntregado = { solicitud ->
                    viewModel.marcarComoEntregado(solicitud)
                },
                onLimpiarError = {
                    viewModel.limpiarError()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    equipos: List<Equipo>,
    solicitudes: List<Solicitud>,
    mensajeError: String?,
    userRole: String,
    userEmail: String,
    onLogout: () -> Unit,
    onSolicitarPrestamo: (Equipo, String, String, Int, String?) -> Boolean,
    onCancelarSolicitud: (Solicitud) -> Unit,
    onMarcarEntregado: (Solicitud) -> Unit,
    onLimpiarError: () -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    var equipoSeleccionado by remember { mutableStateOf<Equipo?>(null) }
    var equipoDetalle by remember { mutableStateOf<Equipo?>(null) }

    var ambiente by remember { mutableStateOf("") }
    var proposito by remember { mutableStateOf("") }
    var duracionText by remember { mutableStateOf("1") }
    var evidenciaUriText by remember { mutableStateOf("content://camera/evidence_default.jpg") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("PréstamoLab CTMA (${if (userRole == "ADMIN") "Administrador" else "Aprendiz"})") },
                    actions = {
                        TextButton(onClick = onLogout) {
                            Text("Salir", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                TabRow(selectedTabIndex = tabIndex) {
                    Tab(
                        selected = tabIndex == 0,
                        onClick = { tabIndex = 0 },
                        text = { Text("Catálogo Equipos") }
                    )
                    Tab(
                        selected = tabIndex == 1,
                        onClick = { tabIndex = 1 },
                        text = { Text(if (userRole == "ADMIN") "Todas las Solicitudes" else "Mis Solicitudes") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Sesión: $userEmail [$userRole]",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (tabIndex == 0) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(equipos) { equipo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (userRole == "APRENDIZ" && equipo.estado == "DISPONIBLE") {
                                    equipoSeleccionado = equipo
                                } else {
                                    equipoDetalle = equipo
                                }
                            }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = equipo.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Categoría: ${equipo.categoria}")
                                Text(text = "Ubicación: ${equipo.ubicacion}")
                                Text(
                                    text = "Estado: ${equipo.estado}",
                                    color = if (equipo.estado == "DISPONIBLE") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                )
                                if (userRole == "ADMIN") {
                                    Text(
                                        text = "[Modo Admin: Gestión de Inventario habilitada]",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(solicitudes) { solicitud ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Equipo: ${solicitud.equipoNombre}", style = MaterialTheme.typography.titleMedium)
                                Text(text = "Destino: ${solicitud.ambienteDestino}")
                                Text(text = "Propósito: ${solicitud.proposito}")
                                Text(text = "Duración: ${solicitud.duracionHoras} hrs")
                                Text(text = "Estado: ${solicitud.estado}", style = MaterialTheme.typography.bodyMedium)

                                if (userRole == "APRENDIZ" && solicitud.estado == "PENDIENTE") {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(onClick = { onCancelarSolicitud(solicitud) }) {
                                            Text("Cancelar")
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(onClick = { onMarcarEntregado(solicitud) }) {
                                            Text("Marcar Entregado")
                                        }
                                    }
                                } else if (userRole == "ADMIN") {
                                    Text(
                                        text = "[Control Administrativo: Visualizando registro de préstamo global]",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        equipoSeleccionado?.let { equipo ->
            AlertDialog(
                onDismissRequest = {
                    equipoSeleccionado = null
                    onLimpiarError()
                },
                title = { Text("Solicitar Préstamo: ${equipo.nombre}") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = ambiente,
                            onValueChange = { ambiente = it },
                            label = { Text("Ambiente de Destino") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = proposito,
                            onValueChange = { proposito = it },
                            label = { Text("Propósito (10-180 caracteres)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = duracionText,
                            onValueChange = { duracionText = it },
                            label = { Text("Duración (1-8 horas)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = evidenciaUriText,
                            onValueChange = { evidenciaUriText = it },
                            label = { Text("Evidencia Fotográfica (URI cámara)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        mensajeError?.let { err ->
                            Text(text = err, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val hrs = duracionText.toIntOrNull() ?: 0
                            val ok = onSolicitarPrestamo(equipo, ambiente, proposito, hrs, evidenciaUriText)
                            if (ok) {
                                equipoSeleccionado = null
                                ambiente = ""
                                proposito = ""
                                duracionText = "1"
                            }
                        }
                    ) {
                        Text("Confirmar con Cámara")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { equipoSeleccionado = null }) { Text("Cancelar") }
                }
            )
        }

        equipoDetalle?.let { equipo ->
            AlertDialog(
                onDismissRequest = { equipoDetalle = null },
                title = { Text("Detalle de Equipo") },
                text = {
                    Column {
                        Text(text = "Nombre: ${equipo.nombre}")
                        Text(text = "Ubicación: ${equipo.ubicacion}")
                        Text(text = "Descripción: ${equipo.descripcion}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Estado actual: ${equipo.estado}",
                            color = if (equipo.estado == "DISPONIBLE") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { equipoDetalle = null }) { Text("Entendido") }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val equiposFicticios = listOf(
        Equipo(
            id = "1",
            nombre = "Multímetro Digital",
            categoria = "Electrónica",
            estado = "DISPONIBLE",
            ubicacion = "Lab 102",
            descripcion = "Multímetro de precisión para pruebas de voltaje"
        )
    )

    val solicitudesFicticias = listOf(
        Solicitud(
            id = "101",
            equipoId = "1",
            equipoNombre = "Multímetro Digital",
            ambienteDestino = "Aula 301",
            proposito = "Práctica de circuitos",
            duracionHoras = 2,
            estado = "PENDIENTE"
        )
    )

    PrestamoLabTheme {
        HomeScreenContent(
            equipos = equiposFicticios,
            solicitudes = solicitudesFicticias,
            mensajeError = null,
            userRole = "APRENDIZ",
            userEmail = "aprendiz@formacion.ctma",
            onLogout = {},
            onSolicitarPrestamo = { _, _, _, _, _ -> true },
            onCancelarSolicitud = {},
            onMarcarEntregado = {},
            onLimpiarError = {}
        )
    }
}
