package com.example.laboralex.ui.screens

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.viewmodel.CreateUserViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(userViewModel: CreateUserViewModel, onContinuePressed: () -> Unit) {
    when (userViewModel.loadingState.collectAsState().value) {
        State.LOADING -> LoadingScreen()
        State.LOADED -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        title = {
                            Text(
                                stringResource(R.string.create_user_welcome),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        })
                },
                floatingActionButton = { ContinueButton(userViewModel, onContinuePressed) }
            ) { UserForm(Modifier.padding(it), userViewModel) }
        }
    }
}

@Composable
private fun ContinueButton(userViewModel: CreateUserViewModel, onContinuePressed: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = {
            if (userViewModel.onContinuePressed()) {
                userViewModel.saveUser()
                onContinuePressed()
            }
        }
    ) {
        Text(stringResource(R.string.continue_string))
    }
}

@Composable
private fun UserForm(modifier: Modifier = Modifier, userViewModel: CreateUserViewModel) {
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
        Column(
            modifier = Modifier
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                stringResource(R.string.personal_data),
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleMedium
            )
            FormTextField(
                modifier = Modifier.padding(6.dp),
                value = userName.value,
                onValueChange = userViewModel::changeName,
                onClearPressed = userViewModel::clearName,
                interactionSource = nameInteractionSource,
                label = { Text(stringResource(R.string.name)) },
                isError = requiredName.value,
                supportingText = stringResource(R.string.mandatory_field)
            )
            Spacer(modifier = Modifier.height(3.dp))

            FormTextField(
                modifier = Modifier.padding(6.dp),
                value = userSurname.value,
                onValueChange = userViewModel::changeSurnames,
                onClearPressed = userViewModel::clearSurnames,
                interactionSource = surnameInteractionSource,
                label = { Text(stringResource(R.string.surname)) },
                isError = requiredSurnames.value,
                supportingText = stringResource(R.string.mandatory_field)
            )
        }

        Column(
            Modifier
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                stringResource(R.string.skills),
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleMedium
            )
            QualitiesForm(userViewModel, Modifier.padding(horizontal = 6.dp))
        }
    }
}

@Composable
private fun QualitiesForm(viewModel: CreateUserViewModel, modifier: Modifier = Modifier) {
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
            Text(stringResource(R.string.suggestions))
            ChipFlowRow(viewModel.allSkills.map { it.name }) {
                viewModel.userSkills.add(it)
            }
        }
    }
}