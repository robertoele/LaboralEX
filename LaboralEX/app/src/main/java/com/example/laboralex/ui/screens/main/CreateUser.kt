package com.example.laboralex.ui.screens.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.R
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.QualitiesForm
import com.example.laboralex.viewmodel.SpecialityViewModel
import com.example.laboralex.viewmodel.UserViewModel


@Composable
fun CreateUser(
    navController: NavController,
    userViewModel: UserViewModel,
    specialityViewModel: SpecialityViewModel,
    activity: ComponentActivity
) {
    val userName = userViewModel.name.collectAsStateWithLifecycle()
    val userSurname = userViewModel.firstSurname.collectAsStateWithLifecycle()
    val userSecondSurname = userViewModel.secondSurname.collectAsStateWithLifecycle()
    val description = userViewModel.description.collectAsStateWithLifecycle()

    Column {
        Text(activity.getString(R.string.create_user_welcome))

        Text(activity.getString(R.string.name))
        TextField(userName.value, userViewModel::changeName)

        Text(activity.getString(R.string.first_surname))
        TextField(userSurname.value, userViewModel::changeSurname)

        Text(activity.getString(R.string.second_surname))
        TextField(userSecondSurname.value, userViewModel::changeSecondSurname)

        Text(activity.getString(R.string.description))
        TextField(description.value, userViewModel::changeDescription)

        QualitiesForm(specialityViewModel)

        Button(onClick = {
            userViewModel.saveUser()
            navController.navigate(NavigationManager.InsertCompaniesScreen)
        }) {
            Text("Continuar")
        }
    }
}