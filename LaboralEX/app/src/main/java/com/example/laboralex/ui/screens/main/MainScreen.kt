package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Company
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val loading = viewModel.loading.collectAsStateWithLifecycle()
    if (loading.value) LoadingScreen()
    else Column {
        Text(
            text = "Hola, ${viewModel.user?.firstName}",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        viewModel.allCompanies.collectAsState().value.forEach { CompanyCard(it) }
    }
}

@Composable
private fun CompanyCard(company: Company) {
    Card(onClick = { }) {
        Text(company.name)
    }
}