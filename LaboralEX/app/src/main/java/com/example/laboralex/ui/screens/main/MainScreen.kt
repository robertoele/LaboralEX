package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.laboralex.database.entity.Application
import com.example.laboralex.database.entity.Company
import com.example.laboralex.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    Column {
        Text("Solicitudes en curso", style = MaterialTheme.typography.titleMedium)
        viewModel.applications.collectAsState().value.forEach { OnGoingCard(it) }
        Text("Empresas que te interesan", style = MaterialTheme.typography.titleMedium)
        viewModel.allCompanies.collectAsState().value.forEach { CompanyCard(it, viewModel) }
    }
}

@Composable
private fun CompanyCard(company: Company, viewModel: MainScreenViewModel) {
    Card(onClick = { viewModel.createJobApplication(company) }) {
        Text(company.name)
    }
}

@Composable
private fun OnGoingCard(application: Application) {
    Card {
        Text(application.id.toString())
        Text(application.requestDate)
    }
}

