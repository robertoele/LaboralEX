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
import androidx.compose.runtime.Composable
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
        val formMadeStateFlow = appStateRepository.formMadeFlow.stateIn(
            lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = AppStateRepository.AppState(false)
        )

        enableEdgeToEdge()
        setContent {
            val appState by formMadeStateFlow.collectAsStateWithLifecycle()
            LaboralEXTheme {
                val userViewModel = hiltViewModel<UserViewModel>()
                val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                val navController = rememberNavController()
                Scaffold(bottomBar = { if (appState.formMade) BottomNavigation() }) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination =
                        if (appState.formMade) NavigationManager.MainScreen
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

    @Composable
    private fun BottomNavigation() {
        NavigationBar {
            NavigationBarItem(
                selected = _homeSelected.collectAsStateWithLifecycle().value,
                onClick = { selectHome() },
                label = { Text("Inicio") },
                icon = { Icon(Icons.Default.Home, contentDescription = null) }
            )
            NavigationBarItem(
                selected = _companiesSelected.collectAsStateWithLifecycle().value,
                onClick = { selectCompanies() },
                label = { Text("Compañías") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) }
            )
            NavigationBarItem(
                selected = _skillsSelected.collectAsStateWithLifecycle().value,
                onClick = { selectSkills() },
                label = { Text("Habilidades") },
                icon = { Icon(Icons.Default.Build, contentDescription = null) }
            )
        }
    }

    private fun selectHome() {
        _homeSelected.value = true
        _companiesSelected.value = false
        _skillsSelected.value = false
    }

    private fun selectCompanies() {
        _companiesSelected.value = true
        _homeSelected.value = false
        _skillsSelected.value = false
    }

    private fun selectSkills() {
        _skillsSelected.value = true
        _homeSelected.value = false
        _companiesSelected.value = false
    }

}