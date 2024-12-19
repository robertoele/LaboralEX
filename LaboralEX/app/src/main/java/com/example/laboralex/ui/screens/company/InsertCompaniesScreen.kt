package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.viewmodel.InsertCompaniesViewModel

@Composable
fun InsertCompaniesScreen(
    navController: NavController,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    if (insertCompaniesViewModel.displayed.isNotEmpty()) {
        MainComposable(navController, insertCompaniesViewModel)
    } else WelcomeScreen(navController)
}

@Composable
private fun MainComposable(
    navController: NavController,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text("Empresas en las que estoy interesado", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(5.dp))
            insertCompaniesViewModel.displayed.forEach {
                CompanyCard(it, listOf()) //TODO
            }
        }
    }
}

@Composable
private fun CompanyCard(company: Company, specialities: List<Speciality>) {
    Card {
        Text(
            text = company.name,
            modifier = Modifier.padding(3.dp),
            textAlign = TextAlign.Center
        )
        ChipFlowRow(specialities)
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    CompanyCard(
        Company(name = "Este"),
        listOf(
            Speciality(name = "Jetpack Compose"),
            Speciality(name = "Kotlin"),
            Speciality(name = "Android"),
            Speciality(name = "Godot"),
            Speciality(name = "Unity"),
            Speciality(name = "Retrofit")
        )
    )
}

@Composable
private fun WelcomeScreen(navController: NavController) {
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