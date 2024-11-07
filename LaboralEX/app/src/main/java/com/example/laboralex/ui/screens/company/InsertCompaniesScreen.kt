package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.viewmodel.CompanyViewModel

@Composable
fun InsertCompaniesScreen(navController: NavController, viewModel: CompanyViewModel) {
    val name = viewModel.name.collectAsStateWithLifecycle()
    Column {
        Text("Incluye algunas empresas en las que estés interesado")
        Text("Nombre")
        TextField(name.value, viewModel::onNameChanged)
    }
}