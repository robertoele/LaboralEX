package com.example.laboralex.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.ui.components.TextFieldWithButton
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
        Text(
            stringResource(R.string.create_user_welcome),
            style = MaterialTheme.typography.headlineLarge
        )

        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                value = userName.value,
                onValueChange = userViewModel::changeName,
                interactionSource = nameInteractionSource,
                label = { Text("Nombre") }
            )
            if (requiredName.value) Text(
                "Este campo es obligatorio",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(3.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                value = userSurname.value,
                onValueChange = userViewModel::changeSurnames,
                interactionSource = surnameInteractionSource,
                label = { Text("Apellidos") }
            )

            if (requiredSurnames.value) Text(
                "Este campo es obligatorio",
                color = MaterialTheme.colorScheme.error
            )

        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text("Aptitudes", style = MaterialTheme.typography.titleMedium)
            QualitiesForm(userViewModel, Modifier.padding(horizontal = 6.dp))
        }
    }
}

@Composable
private fun QualitiesForm(viewModel: UserViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val skill = viewModel.skill.collectAsStateWithLifecycle()
        TextFieldWithButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp),
            value = skill.value,
            onValueChanged = viewModel::changeSkill
        ) {
            if (it.isNotEmpty()) {
                viewModel.changeSkill("")
                viewModel.userSkills.add(it)
            }
        }

        ChipFlowRow(viewModel.userSkills)

        if (viewModel.allSkills.isNotEmpty()) {
            Text("Sugerencias")
            ChipFlowRow(viewModel.allSkills.map { it.name }) {
                viewModel.userSkills.add(it)
            }
        }
    }
}