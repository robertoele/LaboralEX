package com.example.laboralex.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.viewmodel.SpecialityViewModel

@Composable
fun QualitiesForm(viewModel: SpecialityViewModel) {
    val name = viewModel.name.collectAsStateWithLifecycle()
    Text("Especialidades")

    TextField(name.value, viewModel::changeName)
    Button(onClick = {
        viewModel.save()
        viewModel.changeName("")
    }) {
        Text("Agregar")
    }
}