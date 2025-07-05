package com.example.laboralex.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.course.Course
import com.example.laboralex.ui.screens.company.CompanyCard
import com.example.laboralex.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val users = viewModel.userFlow.collectAsStateWithLifecycle()
    val notFinishedCourses = viewModel.notFinishedCoursesFlow.collectAsStateWithLifecycle()
    val sortedCompanies = viewModel.sortedCompaniesFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        text = "Hola, ${users.value.firstOrNull()?.firstName}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 5.dp)
        ) {
            Text(
                "Empresas con las que coincide tu perfil", style = typography.titleMedium
            )
            sortedCompanies.value.forEach { CompanyCard(it) }
            if (notFinishedCourses.value.isNotEmpty()) {
                Text("¡Continúa estos cursos!")
                notFinishedCourses.value.forEach { CourseCard(it) }
            }
        }
    }
}

@Composable
private fun CourseCard(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column {
            Text(course.name, modifier = Modifier.padding(horizontal = 3.dp))
            Text(course.reference ?: "", modifier = Modifier.padding(horizontal = 3.dp))
        }
    }
}