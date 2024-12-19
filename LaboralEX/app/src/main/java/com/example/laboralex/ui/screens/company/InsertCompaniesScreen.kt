package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.viewmodel.InsertCompaniesViewModel

@Composable
fun InsertCompaniesScreen(
    navController: NavController,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    if (insertCompaniesViewModel.displayed.isNotEmpty()) {
        Column {
            insertCompaniesViewModel.displayed.forEach {
                CompanyCard(it, listOf()) //TODO
            }
        }
    } else Intro(navController)
}

@Composable
private fun CompanyCard(company: Company, specialities: List<Speciality>) {
    Text(company.name)
    //TODO
}

@Composable
private fun Intro(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Ahora, añadamos algunas empresas en las que estés interesado",
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { navController.navigate(NavigationManager.CreateCompanyScreen) },
        ) {
            Text("Comenzar")
        }
    }
}