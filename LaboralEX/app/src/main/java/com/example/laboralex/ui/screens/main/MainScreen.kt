package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel) {
    Column {
        Button(onClick = { navController.navigate(NavigationManager.CurriculumEditor) }) {
            Text("Pantalla principal")
        }
    }
}

