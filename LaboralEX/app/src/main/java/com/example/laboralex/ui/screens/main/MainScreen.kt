package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.laboralex.viewmodel.UserViewModel

@Composable
fun MainScreen(navController: NavController, userViewModel: UserViewModel) {
    UserData(userViewModel)
}

@Composable
private fun UserData(userViewModel: UserViewModel) {
    val userName = userViewModel.name.collectAsStateWithLifecycle()

    Column {
        TextField(userName.value, onValueChange = userViewModel::changeName)
        Text(userName.value)
    }

}