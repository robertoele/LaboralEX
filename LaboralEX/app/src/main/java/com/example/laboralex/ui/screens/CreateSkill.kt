package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.viewmodel.CreateSkillViewModel

@Composable
fun CreateSkill(viewModel: CreateSkillViewModel, backNavigation: () -> Unit) {
    val skillField = viewModel.skillField.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¿Qué habilidad quieres añadir?")
            TextField(value = skillField.value, onValueChange = viewModel::changeSkillField)
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(modifier = Modifier.weight(0.5f), onClick = backNavigation) {
                    Text("Cancelar")
                }
                Button(modifier = Modifier.weight(0.5f), onClick = {
                    viewModel.addSkill()
                    backNavigation()
                }) {
                    Text("Añadir")
                }
            }
        }
    }
}