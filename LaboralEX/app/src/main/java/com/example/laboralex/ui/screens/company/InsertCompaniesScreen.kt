package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.viewmodel.CompanyViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InsertCompaniesScreen(
    navController: NavController,
    companyViewModel: CompanyViewModel
) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    var companySpeciality by remember { mutableStateOf("") }
    val specialities = remember { mutableListOf<Speciality>() }
    Column {
        Text("Ahora, añadamos algunas empresas en las que estés interesado")
        Spacer(Modifier.height(10.dp))
        Text("Nombre")
        TextField(name.value, companyViewModel::onNameChanged)
        Text("Especialidades que busca la empresa: ")
        Row {
            TextField(companySpeciality, onValueChange = { companySpeciality = it })
            Button(onClick = {
                specialities.add(Speciality(name = companySpeciality))
                companySpeciality = ""
            }) { Text("Agregar especialidad") }
        }
        Text("Sugerencias")
        FlowRow {
            ChipFlowRow(listOf("Android", "GitHub", "Corutinas", "Dagger-Hilt", "Compose")) {
                specialities.add(Speciality(name = it))
            }
        }

        Row {
            Button(onClick = {
                companyViewModel.save()
            }) { Text("Agregar empresa") }
        }
    }
}