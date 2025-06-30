package com.example.laboralex.ui.screens.company

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.ui.components.ChipFlowRow
import com.example.laboralex.viewmodel.InsertCompaniesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertCompaniesScreen(
    insertCompaniesViewModel: InsertCompaniesViewModel,
    initialForm: Boolean = false,
    onEndPressed: () -> Unit = {},
    onCreatePressed: () -> Unit
) {
    val companiesAdded by insertCompaniesViewModel.companiesAddedFlow.collectAsStateWithLifecycle()
    val companiesSkills by insertCompaniesViewModel.companySkillsFlow.collectAsStateWithLifecycle()
    val allSkills by insertCompaniesViewModel.skillsFlow.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.End)
                        .size(50.dp),
                    shape = CircleShape,
                    onClick = onCreatePressed
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
                if (initialForm) {
                    ExtendedFloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        onClick = {
                            insertCompaniesViewModel.finishForm()
                            onEndPressed()
                        }
                    ) {
                        Text("Finalizar")
                    }
                }
            }
        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        "Empresas",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                })
        }

    ) { innerPadding ->
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ) {
            companiesAdded.forEach { company ->
                val companySkills = companiesSkills.filter { it.companyId == company.id }
                val skills = allSkills.filter { skill ->
                    skill.id in companySkills.map { it.skillId }
                }
                CompanyCard(company, skills)
            }
        }
    }

}

@Composable
private fun CompanyCard(company: Company, skills: List<Skill>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = company.name,
            modifier = Modifier.padding(3.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ChipFlowRow(skills)
    }
}

@Preview
@Composable
private fun CompanyCardPreview() {
    CompanyCard(
        Company(name = "Este"),
        listOf(
            Skill(name = "Jetpack Compose"),
            Skill(name = "Kotlin"),
            Skill(name = "Android"),
            Skill(name = "Godot"),
            Skill(name = "Unity"),
            Skill(name = "Retrofit")
        )
    )
}