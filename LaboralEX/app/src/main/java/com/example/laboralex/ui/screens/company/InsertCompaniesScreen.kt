package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column {
        Text("Ahora, añadamos algunas empresas en las que estés interesado")
        Button(onClick = {
            navController.navigate(NavigationManager.CreateCompanyScreen)
        }) { Text("Comenzar") }
    }
}

@Composable
private fun CompanyCard(company: Company, specialities: List<Speciality>) {

}