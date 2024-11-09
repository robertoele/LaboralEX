package com.example.laboralex.ui.screens.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Text(activity.getString(R.string.create_user_welcome))

        Text(activity.getString(R.string.name))
        TextField(userName.value, userViewModel::changeName)

        TextField(
            value = userSurname.value,
            onValueChange = userViewModel::changeSurname,
            label = { Text(activity.getString(R.string.first_surname)) }
        )

        Text(activity.getString(R.string.second_surname))
        TextField(userSecondSurname.value, userViewModel::changeSecondSurname)

        QualitiesForm(specialityViewModel)

        Button(onClick = {
            userViewModel.saveUser()
            navController.navigate(NavigationManager.InsertCompaniesScreen)
        }) {
            Text("Continuar")
        }
    }
}