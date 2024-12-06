package com.example.laboralex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.viewmodel.SpecialityViewModel

@Composable
fun QualitiesForm(viewModel: SpecialityViewModel) {
    Card {
        val name = viewModel.name.collectAsStateWithLifecycle()
        TextField(
            value = name.value,
            onValueChange = viewModel::changeName,
            trailingIcon = {
                Button(onClick = {
                    viewModel.save(Speciality(name = name.value))
                    viewModel.changeName("")
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        )
        ChipFlowRow(listOf("Android", "GitHub", "Corutinas", "Dagger-Hilt", "Compose")) {
            viewModel.save(Speciality(name = it))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownTextField(viewModel: SpecialityViewModel) {
    var expanded by remember { mutableStateOf(true) }
    Column {
        ExposedDropdownMenuBox(expanded = true, onExpandedChange = { expanded = true }) {
            val name = viewModel.name.collectAsStateWithLifecycle()
            val specialities: List<Speciality> =
                remember { listOf(Speciality(name = "Android"), Speciality(name = "Kotlin")) }
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
                    Button(onClick = {
                        viewModel.save(Speciality(name = name.value))
                        viewModel.changeName("")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )
            ExposedDropdownMenu(expanded = true, onDismissRequest = { expanded = true }) {
                filteredSpecialities.forEach {
                    DropdownMenuItem(text = { Text(it.name) }, onClick = { println("Holo") })
                }
            }
        }
    }
}