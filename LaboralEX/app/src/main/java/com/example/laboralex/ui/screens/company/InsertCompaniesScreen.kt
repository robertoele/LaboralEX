package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.viewmodel.CompanyViewModel
import com.example.laboralex.viewmodel.SpecialityViewModel

@Composable
fun InsertCompaniesScreen(
    navController: NavController,
    companyViewModel: CompanyViewModel,
    specialityViewModel: SpecialityViewModel
) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    val specialities = remember { mutableStateListOf<Speciality>() }
    Column {
        Text("Ahora, añadamos algunas empresas en las que estés interesado")
        Spacer(Modifier.height(10.dp))
        Text("Nombre")
        TextField(name.value, companyViewModel::onNameChanged)
        Text("Especialidades que busca la empresa: ")
        DropdownTextField(
            companyViewModel = companyViewModel,
            specialityViewModel = specialityViewModel,
            specialities = listOf()
        ) {
            specialities.add(Speciality(name = it))
        }
        Row {
            Button(onClick = companyViewModel::save) { Text("Agregar empresa") }
        }
    }
}