package com.example.laboralex.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateUser(userViewModel: UserViewModel, onContinuePressed: () -> Unit) {
    when (userViewModel.loadingState.collectAsState().value) {
        State.LOADING -> LoadingScreen()
        State.LOADED -> {
            Scaffold(
                floatingActionButton = { ContinueButton(userViewModel, onContinuePressed) }
            ) { UserForm(userViewModel, Modifier.padding(it)) }
        }
    }
}

@Composable
private fun ContinueButton(userViewModel: UserViewModel, onContinuePressed: () -> Unit) {
    Button(
        onClick = {
            if (userViewModel.onContinuePressed()) {
                userViewModel.saveUser()
                onContinuePressed()
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
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            FormTextField(
                modifier = Modifier.padding(6.dp),
                value = userName.value,
                onValueChange = userViewModel::changeName,
                onClearPressed = userViewModel::clearName,
                interactionSource = nameInteractionSource,
                label = { Text("Nombre") },
                isError = requiredName.value,
                supportingText = "Este campo es obligatorio"
            )
            Spacer(modifier = Modifier.height(3.dp))

            FormTextField(
                modifier = Modifier.padding(6.dp),
                value = userSurname.value,
                onValueChange = userViewModel::changeSurnames,
                onClearPressed = userViewModel::clearSurnames,
                interactionSource = surnameInteractionSource,
                label = { Text("Apellidos") },
                isError = requiredSurnames.value,
                supportingText = "Este campo es obligatorio"
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 3.dp),
                value = skill.value,
                onValueChange = viewModel::changeSkill,
            )
            Button(onClick = {
                viewModel.userSkills.add(skill.value)
                viewModel.changeSkill("")
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
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