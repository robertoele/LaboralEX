package com.example.laboralex.ui.screens.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
import com.example.laboralex.ui.components.TextFieldWithHeader
import com.example.laboralex.viewmodel.UserViewModel

@Composable
fun CreateUser(
    navController: NavController,
    userViewModel: UserViewModel,
    activity: ComponentActivity
) {
    when (userViewModel.loadingState.collectAsState().value) {
        State.LOADING -> LoadingScreen()
        State.LOADED -> {
            Scaffold(
                floatingActionButton = { ContinueButton(userViewModel, navController) }
            ) { UserForm(userViewModel, activity, Modifier.padding(it)) }
        }
    }
}

@Composable
private fun ContinueButton(userViewModel: UserViewModel, navController: NavController) {
    Button(
        onClick = {
            userViewModel.saveUser()
            navController.navigate(NavigationManager.InsertCompaniesScreen)
        }
    ) {
        Text("Continuar")
    }
}

@Composable
private fun UserForm(
    userViewModel: UserViewModel,
    activity: ComponentActivity,
    modifier: Modifier = Modifier
) {
    val userName = userViewModel.name.collectAsStateWithLifecycle()
    val userSurname = userViewModel.surnames.collectAsStateWithLifecycle()
    Column(modifier = modifier.then(Modifier.verticalScroll(rememberScrollState()))) {
        Text(activity.getString(R.string.create_user_welcome))

        TextFieldWithHeader(
            value = userName.value,
            name = activity.getString(R.string.name),
            onValueChanged = userViewModel::changeName
        )

        TextFieldWithHeader(
            value = userSurname.value,
            name = activity.getString(R.string.surname),
            onValueChanged = userViewModel::changeSurnames
        )

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
        if (viewModel.allSkills.isNotEmpty()) {
            Text("Sugerencias")
            ChipFlowRow(viewModel.allSkills.map { it.name }) {
                viewModel.userSkills.add(it)
            }
        }
        viewModel.userSkills.forEach { Text(it) }
    }
}