package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.ui.components.TextFieldWithHeader
import com.example.laboralex.ui.components.TextRequiredField
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
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
        Column(modifier = Modifier.padding(padding)) {
            TextFieldWithHeader(
                value = name.value,
                name = "Nombre",
                onValueChanged = companyViewModel::changeName,
                interactionSource = nameInteractionSource
            )
            if (requiredName.value) TextRequiredField()
            Spacer(modifier = Modifier.height(3.dp))

            Text("Aptitudes que busca la empresa: ")
            Card {
                DropdownTextField(
                    elements = allSkills.value.map { it.name },
                    value = skillId,
                    onValueChanged = { skillId = it }
                ) {
                    companyViewModel.companySkills.add(it)
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

}