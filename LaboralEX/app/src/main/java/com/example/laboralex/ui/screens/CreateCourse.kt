package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.ui.components.FormTextField
import com.example.laboralex.viewmodel.CreateCourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourse(viewModel: CreateCourseViewModel) {
    val allSkills = viewModel.allSkills.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Añadir curso",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                }
            ) {
                Text("Añadir")
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            //Nombre
            FormTextField(
                value = viewModel.nameField,
                onValueChange = viewModel::changeNameField,
                onClearPressed = viewModel::clearNameField,
                label = { Text("Nombre") }
            )
            //Habilidades que trabaja
            allSkills.value.forEach {
                Text(it.name) //TODO Tarjeta guapa
            }
            //Enlace
            FormTextField(
                value = viewModel.linkField,
                onValueChange = viewModel::changeLinkField,
                onClearPressed = viewModel::clearLinkField,
                label = { Text("Enlace") }
            )
        }

    }
}