package com.example.laboralex.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.viewmodel.UserSkillsViewModel

@Composable
fun UserSkillsScreen(viewModel: UserSkillsViewModel) {
    Column {
        val skill = viewModel.skill.collectAsStateWithLifecycle()
        val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 3.dp),
                value = skill.value,
                onValueChange = viewModel::changeSkill,
            )
            Button(onClick = viewModel::addSkill) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }

        if (allSkills.value.isNotEmpty()) {
            Text("Sugerencias")
            ChipFlowRow(
                chipsList = allSkills.value.map { it.name },
                onSelected = viewModel::addSkill
            )
        }
    }
}