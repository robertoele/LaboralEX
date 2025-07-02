package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.viewmodel.CreateCourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourse(viewModel: CreateCourseViewModel, onAddSelected: () -> Unit) {
    val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()
    val nameField = viewModel.nameField.collectAsStateWithLifecycle()
    val linkField = viewModel.linkField.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Añadir curso",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.addCourse()
                    onAddSelected()
                }
            ) {
                Text("Añadir")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(5.dp)
        ) {
            Text("Nombre")
            FormTextField(
                value = nameField.value,
                onValueChange = viewModel::changeNameField,
                onClearPressed = viewModel::clearNameField,
                label = { Text("Nombre") }
            )
            Text("Enlace al curso")
            FormTextField(
                value = linkField.value,
                onValueChange = viewModel::changeLinkField,
                onClearPressed = viewModel::clearLinkField,
                label = { Text("Enlace") }
            )
            Text("Habilidades utilizadas")
            allSkills.value.forEach { SkillCard(it) }
        }

    }
}

@Composable
private fun SkillCard(skillSelect: SkillSelect) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row {
            Text(
                text = skillSelect.skill.name,
                modifier = Modifier
                    .padding(3.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}