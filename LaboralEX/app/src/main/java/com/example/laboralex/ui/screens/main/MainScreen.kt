package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Application
import com.example.laboralex.database.entity.Company
import com.example.laboralex.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel) {
    Column {
        Text("Solicitudes en curso")
        viewModel.applications.collectAsState().value.forEach { }
        Text("Empresas que te interesan")
        viewModel.allCompanies.collectAsState().value.forEach { CompanyCard(it) }
    }
}

@Composable
private fun CompanyCard(company: Company) {
    Card {
        Text(company.name)
    }
}

@Composable
private fun OnGoingCard(company: Application) {
    Card {
        Text(company.requestDate)

    }
}

