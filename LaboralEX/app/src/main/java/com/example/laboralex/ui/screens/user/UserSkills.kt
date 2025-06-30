package com.example.laboralex.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.viewmodel.UserSkillsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSkillsScreen(viewModel: UserSkillsViewModel) {
    Scaffold(topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            title = {
                Text(
                    "Habilidades",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            val skillField = viewModel.skill.collectAsStateWithLifecycle()
            val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    value = skillField.value,
                    onValueChange = viewModel::changeSkill,
                )
                Button(onClick = viewModel::addSkill) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

            if (allSkills.value.isNotEmpty()) {
                ChipFlowRow(
                    chipsList = allSkills.value.map { it.name },
                    onSelected = viewModel::addSkill
                )
            }
        }
    }

}