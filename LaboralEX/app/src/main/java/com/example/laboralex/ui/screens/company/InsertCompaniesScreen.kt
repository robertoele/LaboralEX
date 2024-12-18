package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.viewmodel.CompanyViewModel

@Composable
fun InsertCompaniesScreen(navController: NavController, companyViewModel: CompanyViewModel) {
    when (companyViewModel.loadingState.collectAsState().value) {
        State.LOADING -> LoadingScreen()
        else -> {
            Scaffold(floatingActionButton = {
                Button(onClick = {}) { Text("Continuar") }
            }) {
                CompaniesForm(modifier = Modifier.padding(it), companyViewModel)
            }
        }
    }
}

@Composable
private fun CompaniesForm(modifier: Modifier = Modifier, companyViewModel: CompanyViewModel) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    var speciality by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text("Ahora, añadamos algunas empresas en las que estés interesado")
        Spacer(Modifier.height(10.dp))
        Text("Nombre")
        TextField(name.value, companyViewModel::onNameChanged)
        Text("Aptitudes que busca la empresa: ")
        Card {
            DropdownTextField(
                elements = companyViewModel.possibleSpecialities.map { it.name },
                value = speciality,
                onValueChanged = { speciality = it }
            ) { }
            if (companyViewModel.possibleSpecialities.isNotEmpty()) {
                Text("Sugerencias")
                ChipFlowRow(companyViewModel.possibleSpecialities.map { it.name })
            }
        }

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = companyViewModel::saveCompany
        ) { Text("Agregar empresa") }
    }
}

@Composable
private fun CompanyCard(company: Company, specialities: List<Speciality>) {

}