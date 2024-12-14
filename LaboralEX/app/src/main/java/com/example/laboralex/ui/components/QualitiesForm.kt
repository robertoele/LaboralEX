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

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownTextField(
    viewModel: SpecialityViewModel,
    specialities: List<Speciality>,
    onSpecialityAdd: (speciality: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val add: (name: String) -> Unit = {
        viewModel.save(Speciality(name = it))
        onSpecialityAdd(it)
        viewModel.changeName("")
        expanded = false
    }
    Column {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            val name = viewModel.name.collectAsStateWithLifecycle()
            val filteredSpecialities =
                specialities.filter { it.name.contains(name.value, ignoreCase = true) }
            TextField(
                value = name.value,
                onValueChange = {
                    viewModel.changeName(it)
                    expanded = specialities.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable),
                trailingIcon = {
                    Button(onClick = { add(name.value) }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                filteredSpecialities.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = { add(it.name) }
                    )
                }
            }
        }
    }
}*/
