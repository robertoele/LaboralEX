package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.viewmodel.CreateCourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourse(viewModel: CreateCourseViewModel, url: String?, onAddSelected: () -> Unit) {
    val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()
    val addedSkills = viewModel.displayedSkills.collectAsStateWithLifecycle()
    val nameField = viewModel.nameField.collectAsStateWithLifecycle()
    val linkField = viewModel.linkField.collectAsStateWithLifecycle()
    val skillField = viewModel.skillField.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.add_course),
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
                Text(stringResource(R.string.add))
            }
        }
    ) { innerPadding ->
        url?.let { viewModel.changeLinkField(it) }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(5.dp)
        ) {
            Text(stringResource(R.string.name))
            FormTextField(
                modifier = Modifier.width(300.dp),
                value = nameField.value,
                onValueChange = viewModel::changeNameField,
                onClearPressed = viewModel::clearNameField,
                label = { Text(stringResource(R.string.name)) }
            )
            Text(stringResource(R.string.course_link))
            FormTextField(
                modifier = Modifier.width(300.dp),
                value = linkField.value,
                onValueChange = viewModel::changeLinkField,
                onClearPressed = viewModel::clearLinkField,
                label = { Text(stringResource(R.string.link)) }
            )
            Text(stringResource(R.string.skills))
            Row(verticalAlignment = Alignment.CenterVertically) {
                FormTextField(
                    modifier = Modifier.width(300.dp),
                    value = skillField.value,
                    onValueChange = viewModel::changeSkillField,
                    onClearPressed = viewModel::clearSkillField
                )
                Button(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    onClick = viewModel::addSkill
                )
                {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
            Text(stringResource(R.string.suggestions))
            ChipFlowRow(allSkills.value, viewModel::addSkill)

            addedSkills.value.forEach { SkillCard(it) }
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
        Row {
            Text(
                text = skill.name,
                modifier = Modifier
                    .padding(3.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}