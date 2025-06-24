package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateCompanyScreen(
    createCompanyViewModel: CreateCompanyViewModel,
    onCompanyAdded: () -> Unit
) {
    val appState by createCompanyViewModel.appStateFlow.collectAsStateWithLifecycle()
    val companies by createCompanyViewModel.companiesStateFlow.collectAsStateWithLifecycle()
    var initialScreen by remember { mutableStateOf(true) }

    if (!appState.formMade && companies.isEmpty() && initialScreen) {
        Column {
            Text("Añadamos algunas empresas")
            Button(onClick = { initialScreen = false }) { Text("Continuar") }
        }
    } else {
        val name = createCompanyViewModel.name.collectAsStateWithLifecycle()
        val allSkills = createCompanyViewModel.skillsFlow.collectAsStateWithLifecycle()
        val requiredName = createCompanyViewModel.requiredName.collectAsStateWithLifecycle()
        val emptySkill = createCompanyViewModel.emptySkill.collectAsStateWithLifecycle()

        val nameInteractionSource = remember { MutableInteractionSource() }
        val skillInteractionSource = remember { MutableInteractionSource() }

        LaunchedEffect(nameInteractionSource) {
            nameInteractionSource.interactions.collectLatest {
                if (it is PressInteraction.Press) createCompanyViewModel.changeRequired()
            }
        }

        LaunchedEffect(skillInteractionSource) {
            skillInteractionSource.interactions.collectLatest {
                if (it is PressInteraction.Press) createCompanyViewModel.changeEmptySkill()
            }
        }

        var skillName by remember { mutableStateOf("") }

        Scaffold(
            floatingActionButton = {
                Button(
                    onClick = {
                        if (createCompanyViewModel.onContinuePressed()) {
                            onCompanyAdded()
                            createCompanyViewModel.onCompanyAdded()
                        }
                    }
                ) { Text("Agregar empresa") }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 6.dp)
            ) {
                Text("Nombre")
                FormTextField(
                    value = name.value,
                    onValueChange = createCompanyViewModel::changeName,
                    onClearPressed = createCompanyViewModel::clearName,
                    interactionSource = nameInteractionSource,
                    label = { Text("Nombre") },
                    isError = requiredName.value,
                    supportingText = "Este campo es obligatorio"
                )
                Spacer(modifier = Modifier.height(3.dp))

                Text("Aptitudes que busca la empresa")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FormTextField(
                        value = skillName,
                        onValueChange = { skillName = it },
                        onClearPressed = { skillName = "" },
                        placeHolder = { Text("Java, C#, Android, etc") },
                        isError = emptySkill.value,
                        supportingText = "El campo está vacío",
                        errorIcon = {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onError
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(errorBorderColor = Color.Yellow)
                    )
                    Button(onClick = {
                        if (skillName.isNotBlank()) {
                            createCompanyViewModel.companySkills.add(skillName)
                            skillName = ""
                        } else createCompanyViewModel.changeEmptySkill()
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }

                ChipFlowRow(createCompanyViewModel.companySkills)

                if (allSkills.value.isNotEmpty()) {
                    Text("Sugerencias")
                    ChipFlowRow(allSkills.value.map { it.name }) {
                        createCompanyViewModel.companySkills.add(it)
                    }
                }
            }
        }
    }

}