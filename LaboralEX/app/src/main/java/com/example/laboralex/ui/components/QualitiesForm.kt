package com.example.laboralex.ui.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.viewmodel.SpecialityViewModel

@Composable
fun QualitiesForm(viewModel: SpecialityViewModel, specialities: List<Speciality>) {
    val specialitiesList = remember { mutableStateListOf<String>() }
    val specialitiesNames = remember { specialities.map { it.name } }
    Card {
        val name = viewModel.name.collectAsStateWithLifecycle()
        DropdownTextField(
            elements = specialitiesNames,
            value = name.value,
            onValueChanged = viewModel::changeName
        ) { valueSelected ->
            viewModel.save(Speciality(name = valueSelected))
            viewModel.changeName("")
        }
        ChipFlowRow(specialitiesList) { }
    }
}