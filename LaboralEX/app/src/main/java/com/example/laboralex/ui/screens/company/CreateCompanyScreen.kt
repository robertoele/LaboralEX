package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.ui.components.TextFieldWithButton
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CreateCompanyScreen(
    navController: NavController,
    companyViewModel: CreateCompanyViewModel,
    insertCompaniesViewModel: InsertCompaniesViewModel
) {
    val name = companyViewModel.name.collectAsStateWithLifecycle()
    val allSkills = companyViewModel.allSkills.collectAsStateWithLifecycle()
    val requiredName = companyViewModel.requiredName.collectAsStateWithLifecycle()

    val nameInteractionSource = remember { MutableInteractionSource() }

    LaunchedEffect(nameInteractionSource) {
        nameInteractionSource.interactions.collectLatest {
            if (it is PressInteraction.Press) companyViewModel.changeRequired()
        }
    }

    var skillId by remember { mutableStateOf("") }
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    if (companyViewModel.onContinuePressed()) {
                        insertCompaniesViewModel.viewModelScope.launch {
                            companyViewModel.saveCompany()
                            companyViewModel.companySkills.clear()
                            navController.popBackStack()
                        }
                        companyViewModel.changeName("")
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
        ) {
            FormTextField(
                modifier = Modifier.padding(6.dp),
                value = name.value,
                onValueChange = companyViewModel::changeName,
                onClearPressed = companyViewModel::clearName,
                interactionSource = nameInteractionSource,
                label = { Text("Nombre") },
                isError = requiredName.value
            )
            Spacer(modifier = Modifier.height(3.dp))

            Text("Aptitudes que busca la empresa: ")
            TextFieldWithButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                value = skillId,
                onValueChanged = { skillId = it }
            ) {
                companyViewModel.companySkills.add(it)
                skillId = ""
            }

            ChipFlowRow(companyViewModel.companySkills)

            if (allSkills.value.isNotEmpty()) {
                Text("Sugerencias")
                ChipFlowRow(allSkills.value.map { it.name }) {
                    companyViewModel.companySkills.add(it)
                }
            }
        }
    }

}