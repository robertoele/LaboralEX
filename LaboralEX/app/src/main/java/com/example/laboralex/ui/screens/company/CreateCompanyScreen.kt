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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateCompanyScreen(
    navController: NavController,
    companyViewModel: CreateCompanyViewModel,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    var skillId by remember { mutableStateOf("") }
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    companyViewModel.viewModelScope.launch {
                        companyViewModel.saveCompany()
                        companyViewModel.companySkills.clear()
                    }
                    navController.popBackStack()
                    companyViewModel.changeName("")
                }
            ) { Text("Agregar empresa") }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Nombre")
            TextField(name.value, companyViewModel::changeName)
            Text("Aptitudes que busca la empresa: ")
            Card {
                DropdownTextField(
                    elements = companyViewModel.allSkills.map { it.name },
                    value = skillId,
                    onValueChanged = { skillId = it }
                ) {
                    companyViewModel.companySkills.add(it)
                }
                if (companyViewModel.allSkills.isNotEmpty()) {
                    Text("Sugerencias")
                    ChipFlowRow(companyViewModel.allSkills.map { it.name })
                }
            }
            companyViewModel.companySkills.forEach { Text(it) }
        }
    }

}