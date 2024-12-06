package com.example.laboralex.ui.screens.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.QualitiesForm
import com.example.laboralex.ui.components.TextFieldWithHeader
import com.example.laboralex.viewmodel.SpecialityViewModel
import com.example.laboralex.viewmodel.UserSpecialityViewModel
import com.example.laboralex.viewmodel.UserViewModel


@Composable
fun CreateUser(
    navController: NavController,
    userViewModel: UserViewModel,
    specialityViewModel: SpecialityViewModel,
    userSpecialityViewModel: UserSpecialityViewModel,
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

        Text("Especialidades")
        QualitiesForm(specialityViewModel)

        Button(onClick = {
            userViewModel.saveUser()
            navController.navigate(NavigationManager.InsertCompaniesScreen)
        }) {
            Text("Continuar")
        }
    }
}