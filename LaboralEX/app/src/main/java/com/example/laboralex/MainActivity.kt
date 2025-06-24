package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboralex.database.AppStateRepository
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.screens.company.CreateCompanyScreen
import com.example.laboralex.ui.screens.company.InsertCompaniesScreen
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.screens.user.CreateUser
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import com.example.laboralex.viewmodel.MainScreenViewModel
import com.example.laboralex.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _homeSelected = MutableStateFlow(true)
    private val _companiesSelected = MutableStateFlow(false)
    private val _skillsSelected = MutableStateFlow(false)

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
            val appState by formMadeStateFlow.collectAsStateWithLifecycle()
            if (appState == null) LoadingScreen()
            else {
                LaboralEXTheme {
                    val userViewModel = hiltViewModel<UserViewModel>()
                    val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                    val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    val navController = rememberNavController()
                    Scaffold(bottomBar = {
                        if (appState?.formMade == true) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = _homeSelected.collectAsStateWithLifecycle().value,
                                    onClick = {
                                        navController.navigate(NavigationManager.MainScreen) {
                                            launchSingleTop = true
                                        }
                                        _homeSelected.value = true
                                        _companiesSelected.value = false
                                        _skillsSelected.value = false
                                    },
                                    label = { Text("Inicio") },
                                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                                )
                                NavigationBarItem(
                                    selected = _companiesSelected.collectAsStateWithLifecycle().value,
                                    onClick = {
                                        navController.navigate(NavigationManager.InsertCompaniesScreen) {
                                            launchSingleTop = true
                                        }
                                        _companiesSelected.value = true
                                        _homeSelected.value = false
                                        _skillsSelected.value = false
                                    },
                                    label = { Text("Compañías") },
                                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                                )
                                NavigationBarItem(
                                    selected = _skillsSelected.collectAsStateWithLifecycle().value,
                                    onClick = {
                                        navController.navigate(NavigationManager.CreateUserScreen) {
                                            launchSingleTop = true
                                        }
                                        _skillsSelected.value = true
                                        _homeSelected.value = false
                                        _companiesSelected.value = false
                                    },
                                    label = { Text("Habilidades") },
                                    icon = { Icon(Icons.Default.Build, contentDescription = null) }
                                )
                            }
                        }
                    }) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination =
                            if (appState?.formMade == true) NavigationManager.MainScreen
                            else NavigationManager.CreateUserScreen,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable<NavigationManager.MainScreen> {
                                MainScreen(mainScreenViewModel)
                            }
                            composable<NavigationManager.CreateUserScreen> {
                                CreateUser(userViewModel) {
                                    navController.navigate(NavigationManager.CreateCompanyScreen)
                                }
                            }
                            composable<NavigationManager.CreateCompanyScreen> {
                                CreateCompanyScreen(createCompanyViewModel) {
                                    navController.navigate(NavigationManager.InsertCompaniesScreen)
                                }
                            }
                            composable<NavigationManager.InsertCompaniesScreen> {
                                InsertCompaniesScreen(
                                    insertCompaniesViewModel,
                                    onContinuePressed = {
                                        navController.navigate(NavigationManager.MainScreen)
                                    }, onCreatePressed = {
                                        navController.navigate(NavigationManager.CreateCompanyScreen)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}