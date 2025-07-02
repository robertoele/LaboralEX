package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.laboralex.database.AppStateRepository
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.screens.CoursesScreen
import com.example.laboralex.ui.screens.CreateCourse
import com.example.laboralex.ui.screens.CreateSkill
import com.example.laboralex.ui.screens.CreateUser
import com.example.laboralex.ui.screens.UserSkillsScreen
import com.example.laboralex.ui.screens.company.CreateCompanyScreen
import com.example.laboralex.ui.screens.company.InsertCompaniesScreen
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CoursesViewModel
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.CreateCourseViewModel
import com.example.laboralex.viewmodel.CreateSkillViewModel
import com.example.laboralex.viewmodel.CreateUserViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import com.example.laboralex.viewmodel.MainScreenViewModel
import com.example.laboralex.viewmodel.UserSkillsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appStateRepository: AppStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val formMadeStateFlow: StateFlow<AppStateRepository.AppState?> =
            appStateRepository.formMadeFlow.stateIn(
                lifecycleScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = null
            )

        enableEdgeToEdge()
        setContent {
            var homeSelected by remember { mutableStateOf(true) }
            var companiesSelected by remember { mutableStateOf(false) }
            var skillsSelected by remember { mutableStateOf(false) }
            var experienceSelected by remember { mutableStateOf(false) }

            val appState by formMadeStateFlow.collectAsStateWithLifecycle()
            if (appState == null) LoadingScreen()
            else {
                LaboralEXTheme {
                    val userViewModel = hiltViewModel<CreateUserViewModel>()
                    val userSkillsViewModel = hiltViewModel<UserSkillsViewModel>()
                    val createSkillViewModel = hiltViewModel<CreateSkillViewModel>()
                    val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                    val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                    val courseViewModel = hiltViewModel<CoursesViewModel>()
                    val createCourseViewModel = hiltViewModel<CreateCourseViewModel>()
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            if (appState?.formMade == true) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ) {
                                    NavigationBarItem(
                                        selected = homeSelected,
                                        onClick = {
                                            navController.navigate(NavigationManager.MainScreen) {
                                                popUpTo<NavigationManager.MainScreen>()
                                                launchSingleTop = true
                                            }
                                            homeSelected = true
                                            companiesSelected = false
                                            skillsSelected = false
                                            experienceSelected = false
                                        },
                                        label = { Text("Inicio") },
                                        icon = {
                                            Icon(
                                                Icons.Default.Home,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    NavigationBarItem(
                                        selected = companiesSelected,
                                        onClick = {
                                            navController.navigate(NavigationManager.InsertCompaniesScreen) {
                                                popUpTo<NavigationManager.InsertCompaniesScreen>()
                                                launchSingleTop = true
                                            }
                                            companiesSelected = true
                                            homeSelected = false
                                            skillsSelected = false
                                            experienceSelected = false
                                        },
                                        label = { Text("Compañías") },
                                        icon = {
                                            Icon(
                                                Icons.Default.Person,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    NavigationBarItem(
                                        selected = skillsSelected,
                                        onClick = {
                                            navController.navigate(NavigationManager.UserSkills) {
                                                popUpTo<NavigationManager.UserSkills>()
                                                launchSingleTop = true
                                            }
                                            skillsSelected = true
                                            homeSelected = false
                                            companiesSelected = false
                                            experienceSelected = false
                                        },
                                        label = { Text("Habilidades") },
                                        icon = {
                                            Icon(
                                                Icons.Default.Build,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                    NavigationBarItem(
                                        selected = experienceSelected,
                                        onClick = {
                                            navController.navigate(NavigationManager.CoursesScreen) {
                                                popUpTo<NavigationManager.CoursesScreen>()
                                                launchSingleTop = true
                                            }
                                            experienceSelected = true
                                            skillsSelected = false
                                            homeSelected = false
                                            companiesSelected = false
                                        },
                                        label = { Text("Experiencia") },
                                        icon = {
                                            Icon(
                                                Icons.Default.Face,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            modifier = Modifier.padding(padding),
                            startDestination =
                            if (appState?.formMade == true) NavigationManager.AppRoot
                            else NavigationManager.CreateUserScreen
                        ) {
                            composable<NavigationManager.CreateUserScreen> {
                                CreateUser(userViewModel) {
                                    navController.navigate(NavigationManager.CreateCompanyScreen)
                                }
                            }
                            composable<NavigationManager.CreateCompanyScreen> {
                                CreateCompanyScreen(createCompanyViewModel) {
                                    navController.navigate(NavigationManager.InsertCompaniesScreenForm)
                                }
                            }
                            composable<NavigationManager.InsertCompaniesScreenForm> {
                                InsertCompaniesScreen(
                                    insertCompaniesViewModel,
                                    initialForm = true,
                                    onEndPressed = {
                                        navController.navigate(NavigationManager.AppRoot)
                                    }, onCreatePressed = {
                                        navController.navigate(NavigationManager.CreateCompanyScreen)
                                    }
                                )
                            }
                            navigation<NavigationManager.AppRoot>(startDestination = NavigationManager.MainScreen) {
                                composable<NavigationManager.MainScreen> {
                                    MainScreen(mainScreenViewModel)
                                }
                                composable<NavigationManager.UserSkills> {
                                    BackHandler {
                                        navController.navigate(NavigationManager.MainScreen) {
                                            popUpTo<NavigationManager.MainScreen> {
                                                saveState = true
                                            }
                                        }
                                    }
                                    UserSkillsScreen(userSkillsViewModel) {
                                        navController.navigate(NavigationManager.CreateSkill)
                                    }
                                }
                                composable<NavigationManager.CreateSkill> {
                                    CreateSkill(
                                        viewModel = createSkillViewModel,
                                        backNavigation = navController::popBackStack
                                    )
                                }
                                composable<NavigationManager.CreateCompanyScreen> {
                                    CreateCompanyScreen(createCompanyViewModel) {
                                        navController.navigate(NavigationManager.InsertCompaniesScreen)
                                    }
                                }
                                composable<NavigationManager.CoursesScreen> {
                                    CoursesScreen(courseViewModel) {
                                        navController.navigate(NavigationManager.CreateCourseScreen)
                                    }
                                }
                                composable<NavigationManager.CreateCourseScreen> {
                                    CreateCourse(createCourseViewModel)
                                }
                                composable<NavigationManager.InsertCompaniesScreen> {
                                    BackHandler {
                                        navController.navigate(NavigationManager.MainScreen) {
                                            popUpTo<NavigationManager.MainScreen> {
                                                saveState = true
                                            }
                                        }
                                    }
                                    InsertCompaniesScreen(insertCompaniesViewModel, false) {
                                        navController.navigate(NavigationManager.CreateCompanyScreen)
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}