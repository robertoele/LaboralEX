package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel

@Composable
fun CreateCompanyScreen(
    navController: NavController,
    companyViewModel: CreateCompanyViewModel,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    var speciality by remember { mutableStateOf("") }
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {}
            ) { Text("Agregar empresa") }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Nombre")
            TextField(name.value, companyViewModel::onNameChanged)
            Text("Aptitudes que busca la empresa: ")
            Card {
                DropdownTextField(
                    elements = companyViewModel.possibleSpecialities.map { it.name },
                    value = speciality,
                    onValueChanged = { speciality = it }
                ) {
                    companyViewModel.displayed.add(Speciality(name = it))
                }
                if (companyViewModel.possibleSpecialities.isNotEmpty()) {
                    Text("Sugerencias")
                    ChipFlowRow(companyViewModel.possibleSpecialities.map { it.name })
                }
            }
            companyViewModel.displayed.forEach { Text(it.name) }
        }
    }

}