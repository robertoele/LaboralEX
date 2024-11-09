package com.example.laboralex.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.viewmodel.SpecialityViewModel

@Composable
fun QualitiesForm(viewModel: SpecialityViewModel) {
    val name = viewModel.name.collectAsStateWithLifecycle()
    Text("Especialidades")
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
    Text("Sugerencias")
    ChipFlowRow(listOf("Android", "GitHub", "Corutinas", "Dagger-Hilt", "Compose")) {
        viewModel.save(Speciality(name = it))
    }
    Button(onClick = {
        viewModel.save(Speciality(name = name.value))
        viewModel.changeName("")
    }) {
        Text("Agregar")
    }
}