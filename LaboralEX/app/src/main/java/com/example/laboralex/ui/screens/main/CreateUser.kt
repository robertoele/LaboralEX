package com.example.laboralex.ui.screens.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.viewmodel.UserViewModel


@Composable
fun CreateUser(viewModel: UserViewModel, activity: ComponentActivity) {
    val userName = viewModel.name.collectAsStateWithLifecycle()
    val userSurname = viewModel.firstSurname.collectAsStateWithLifecycle()
    val userSecondSurname = viewModel.secondSurname.collectAsStateWithLifecycle()
    val description = viewModel.description.collectAsStateWithLifecycle()

    Column {
        Text(activity.getString(R.string.create_user_welcome))

        Text(activity.getString(R.string.name))
        TextField(userName.value, viewModel::changeName)

        Text(activity.getString(R.string.first_surname))
        TextField(userSurname.value, viewModel::changeSurname)

        Text(activity.getString(R.string.second_surname))
        TextField(userSecondSurname.value, viewModel::changeSecondSurname)

        Text(activity.getString(R.string.description))
        TextField(description.value, viewModel::changeDescription)

        Button(onClick = viewModel::saveUser) {
            Text("Guardar")
        }
    }
}