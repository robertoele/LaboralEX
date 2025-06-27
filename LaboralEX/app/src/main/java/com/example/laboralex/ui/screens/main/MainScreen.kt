package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.laboralex.database.entity.Company
import com.example.laboralex.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    Column {
        Text("Empresas que te interesan", style = MaterialTheme.typography.titleMedium)
        viewModel.allCompanies.collectAsState().value.forEach { CompanyCard(it) }
    }
}

@Composable
private fun CompanyCard(company: Company) {
    Card(onClick = { }) {
        Text(company.name)
    }
}