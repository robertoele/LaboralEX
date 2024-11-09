package com.example.laboralex.ui.components

import androidx.compose.material3.Button
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
    TextField(name.value, viewModel::changeName)
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