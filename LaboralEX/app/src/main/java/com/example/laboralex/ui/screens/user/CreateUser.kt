package com.example.laboralex.ui.screens.user

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.ui.components.TextFieldWithHeader
import com.example.laboralex.ui.components.TextRequiredField
import com.example.laboralex.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateUser(navController: NavController, userViewModel: UserViewModel) {
    when (userViewModel.loadingState.collectAsState().value) {
        State.LOADING -> LoadingScreen()
        State.LOADED -> {
            Scaffold(
                floatingActionButton = { ContinueButton(userViewModel, navController) }
            ) { UserForm(userViewModel, Modifier.padding(it)) }
        }
    }
}

@Composable
private fun ContinueButton(userViewModel: UserViewModel, navController: NavController) {
    Button(
        onClick = {
            if (userViewModel.onContinuePressed()) {
                userViewModel.saveUser()
                navController.navigate(NavigationManager.InsertCompaniesScreen)
            }
        }
    ) {
        Text("Continuar")
    }
}

@Composable
private fun UserForm(userViewModel: UserViewModel, modifier: Modifier = Modifier) {
    val userName = userViewModel.name.collectAsStateWithLifecycle()
    val userSurname = userViewModel.surnames.collectAsStateWithLifecycle()

    val requiredName = userViewModel.requiredName.collectAsStateWithLifecycle()
    val requiredSurnames = userViewModel.requiredSurnames.collectAsStateWithLifecycle()

    val nameInteractionSource = remember { MutableInteractionSource() }
    val surnameInteractionSource = remember { MutableInteractionSource() }

    LaunchedEffect(nameInteractionSource) {
        nameInteractionSource.interactions.collectLatest { value ->
            if (value is PressInteraction.Press) userViewModel.changeRequiredName()
        }
    }

    LaunchedEffect(surnameInteractionSource) {
        surnameInteractionSource.interactions.collectLatest { value ->
            if (value is PressInteraction.Press) userViewModel.changeRequiredSurnames()
        }
    }

    Column(modifier = modifier.then(Modifier.verticalScroll(rememberScrollState()))) {
        Text(stringResource(R.string.create_user_welcome))

        TextFieldWithHeader(
            value = userName.value,
            name = stringResource(R.string.name),
            interactionSource = nameInteractionSource,
            onValueChanged = userViewModel::changeName
        )
        if (requiredName.value) TextRequiredField()
        Spacer(modifier = Modifier.height(3.dp))

        TextFieldWithHeader(
            value = userSurname.value,
            name = stringResource(R.string.surname),
            interactionSource = surnameInteractionSource,
            onValueChanged = userViewModel::changeSurnames
        )
        if (requiredSurnames.value) TextRequiredField()
        Spacer(modifier = Modifier.height(3.dp))

        Text("Aptitudes")
        QualitiesForm(userViewModel)
    }
}

@Composable
private fun QualitiesForm(viewModel: UserViewModel) {
    val skillsNames = remember { viewModel.allSkills.map { it.name } }
    Card {
        val skill = viewModel.skill.collectAsStateWithLifecycle()
        DropdownTextField(
            elements = skillsNames,
            value = skill.value,
            onValueChanged = viewModel::changeSkill
        ) {
            if (it.isNotEmpty()) {
                viewModel.changeSkill("")
                viewModel.userSkills.add(it)
            }
        }

        viewModel.userSkills.forEach { Text(it) }
        
        if (viewModel.allSkills.isNotEmpty()) {
            Text("Sugerencias")
            ChipFlowRow(viewModel.allSkills.map { it.name }) {
                viewModel.userSkills.add(it)
            }
        }
    }
}