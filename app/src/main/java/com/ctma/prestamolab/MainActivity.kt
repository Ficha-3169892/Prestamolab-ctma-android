package com.ctma.prestamolab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ctma.prestamolab.ui.screen.HomeScreen
import com.ctma.prestamolab.ui.theme.PrestamoLabTheme
import com.ctma.prestamolab.ui.viewmodel.PrestamoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PrestamoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrestamoLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}