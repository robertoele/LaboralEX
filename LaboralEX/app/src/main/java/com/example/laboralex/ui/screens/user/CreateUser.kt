package com.example.laboralex.ui.screens.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.components.DropdownTextField
import com.example.laboralex.ui.components.TextFieldWithHeader
import com.example.laboralex.viewmodel.UserViewModel

@Composable
fun CreateUser(
    navController: NavController,
    userViewModel: UserViewModel,
    specialities: List<Speciality>,
    activity: ComponentActivity
) {
    val userName = userViewModel.name.collectAsStateWithLifecycle()
    val userSurname = userViewModel.surnames.collectAsStateWithLifecycle()

    Column(Modifier.verticalScroll(rememberScrollState())) {
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
        QualitiesForm(userViewModel, specialities)

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                userViewModel.saveUser()
                navController.navigate(NavigationManager.InsertCompaniesScreen)
            }
        ) {
            Text("Continuar")
        }
    }
}

@Composable
private fun QualitiesForm(viewModel: UserViewModel, specialities: List<Speciality>) {
    val specialitiesNames = remember { specialities.map { it.name } }
    Card {
        val skill = viewModel.skill.collectAsStateWithLifecycle()
        DropdownTextField(
            elements = specialitiesNames,
            value = skill.value,
            onValueChanged = viewModel::changeSkill
        ) {
            viewModel.changeSkill("")
        }
        Text("Sugerencias")
        ChipFlowRow(specialities.map { it.name }) {
        }
        specialities.forEach { Text(it.name) }
    }
}