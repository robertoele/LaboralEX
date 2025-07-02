package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.viewmodel.UserSkillsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSkillsScreen(viewModel: UserSkillsViewModel, onCreatePressed: () -> Unit) {
    Scaffold(
        topBar = {
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
                },
                actions = {
                    IconButton(onClick = onCreatePressed) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Crear habilidad",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()
            allSkills.value.forEach { SkillCard(it) }
        }
    }

}

@Composable
private fun SkillCard(skill: Skill) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = skill.name,
                modifier = Modifier
                    .padding(3.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.Edit, contentDescription = "Editar habilidad")
            }
        }
    }
}