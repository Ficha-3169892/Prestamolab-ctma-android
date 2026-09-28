package com.ctma.prestamolab.ui.screen

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@Composable
fun LoginScreen(
    onLoginSuccess: (email: String, role: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PréstamoLab CTMA",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Control e Inventario TIC",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.titleMedium
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    errorMessage?.let { err ->
                        Text(
                            text = err,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            val trimmedEmail = email.trim().lowercase()
                            val trimmedPass = password.trim()

                            when {
                                trimmedEmail == "aprendiz@formacion.ctma" && trimmedPass == "aprendiz123" -> {
                                    onLoginSuccess(trimmedEmail, "APRENDIZ")
                                }
                                trimmedEmail == "admin@formacion.ctma" && trimmedPass == "admin123" -> {
                                    onLoginSuccess(trimmedEmail, "ADMIN")
                                }
                                trimmedEmail.isBlank() || trimmedPass.isBlank() -> {
                                    errorMessage = "Por favor ingrese correo y contraseña"
                                }
                                else -> {
                                    errorMessage = "Credenciales incorrectas. Pruebe:\naprendiz@formacion.ctma / aprendiz123\nadmin@formacion.ctma / admin123"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ingresar")
                    }

                    OutlinedButton(
                        onClick = {
                            val activity = context as? FragmentActivity
                            if (activity != null) {
                                val executor = ContextCompat.getMainExecutor(context)
                                val biometricPrompt = BiometricPrompt(activity, executor,
                                    object : BiometricPrompt.AuthenticationCallback() {
                                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                            super.onAuthenticationSucceeded(result)
                                            val assignedEmail = if (email.contains("admin")) "admin@formacion.ctma" else "aprendiz@formacion.ctma"
                                            val assignedRole = if (email.contains("admin")) "ADMIN" else "APRENDIZ"
                                            onLoginSuccess(assignedEmail, assignedRole)
                                        }

                                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                            super.onAuthenticationError(errorCode, errString)
                                            errorMessage = "Autenticación biométrica fallida: $errString"
                                        }
                                    })

                                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                                    .setTitle("PréstamoLab CTMA — Acceso Biométrico")
                                    .setSubtitle("Confirme su identidad con huella digital o PIN")
                                    .setNegativeButtonText("Cancelar")
                                    .build()

                                biometricPrompt.authenticate(promptInfo)
                            } else {
                                errorMessage = "Biometría no disponible en este contexto"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ingresar con Biometría (Huella)")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    email = "aprendiz@formacion.ctma"
                    password = "aprendiz123"
                }
            ) {
                Text("Usar acceso de Aprendiz demo")
            }

            TextButton(
                onClick = {
                    email = "admin@formacion.ctma"
                    password = "admin123"
                }
            ) {
                Text("Usar acceso de Administrador demo")
            }
        }
    }
}
