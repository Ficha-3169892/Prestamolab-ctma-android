package com.ctma.prestamolab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ctma.prestamolab.data.datastore.UserPreferencesRepository
import com.ctma.prestamolab.data.local.AppDatabase
import com.ctma.prestamolab.data.repository.RoomRepository
import com.ctma.prestamolab.ui.screen.HomeScreen
import com.ctma.prestamolab.ui.screen.LoginScreen
import com.ctma.prestamolab.ui.theme.PrestamoLabTheme
import com.ctma.prestamolab.ui.viewmodel.PrestamoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PrestamoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getInstance(applicationContext)
                val repository = RoomRepository(
                    objetoPrestamoDao = db.objetoPrestamoDao(),
                    equipoDao = db.equipoDao()
                )
                val preferencesRepo = UserPreferencesRepository(applicationContext)
                return PrestamoViewModel(
                    roomRepository = repository,
                    userPreferencesRepository = preferencesRepo
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sessionEmail by viewModel.sessionEmail.collectAsStateWithLifecycle()
            val sessionRole by viewModel.sessionRole.collectAsStateWithLifecycle()

            PrestamoLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (sessionEmail.isNullOrBlank()) {
                        LoginScreen(
                            onLoginSuccess = { email, role ->
                                viewModel.iniciarSesion(email, role)
                            }
                        )
                    } else {
                        HomeScreen(
                            viewModel = viewModel,
                            userRole = sessionRole ?: "APRENDIZ",
                            userEmail = sessionEmail ?: "",
                            onLogout = {
                                viewModel.cerrarSesion()
                            }
                        )
                    }
                }
            }
        }
    }
}
