package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.database.entity.course.CourseWithSkills
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.ui.theme.extendedLight
import com.example.laboralex.viewmodel.CoursesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(viewModel: CoursesViewModel, onCreatePressed: () -> Unit) {
    val coursesList = viewModel.courseList.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = colorScheme.secondaryContainer,
                contentColor = colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(50.dp),
                shape = CircleShape,
                onClick = onCreatePressed
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.courses_title),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = topAppBarColors(
                    containerColor = colorScheme.surfaceContainer,
                    titleContentColor = colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            coursesList.value.forEach { CourseCard(it, viewModel) }
        }
    }
}

@Composable
private fun CourseCard(course: CourseWithSkills, viewModel: CoursesViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                course.course.name,
                modifier = Modifier.padding(horizontal = 3.dp),
                color = colorScheme.onPrimaryContainer
            )
            Text(course.course.reference ?: "", color = colorScheme.onPrimaryContainer)
            ChipFlowRow(course.skills)
            val (ongoing, color) =
                if (course.course.finished) Pair(
                    stringResource(R.string.course_completed),
                    extendedLight.success.color
                )
                else Pair(stringResource(R.string.ongoing), colorScheme.onPrimaryContainer)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    ongoing,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = color,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .weight(1f)
                )
                RadioButton(course.course.finished, onClick = {
                    viewModel.changeCourseFinished(course.course.copy(finished = !course.course.finished))
                })
            }
        }
    }
}