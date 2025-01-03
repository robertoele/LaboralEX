package com.example.laboralex.ui.screens.curriculum

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.laboralex.viewmodel.CurriculumEditorViewModel

@Composable
fun CurriculumEditor(viewModel: CurriculumEditorViewModel) {
    Column {
        Button(onClick = viewModel::savePDF) {
            Text("Guardar")
        }
    }
}