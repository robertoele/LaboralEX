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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.entity.User
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.components.LoadingScreen
import com.example.laboralex.ui.components.State
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _loadingState = MutableStateFlow(State.LOADING)
    private val _homeSelected = MutableStateFlow(true)
    private val _companiesSelected = MutableStateFlow(false)
    private val _skillsSelected = MutableStateFlow(false)

    private var user: User? = null

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            user = userDao.getAll().firstOrNull()
            _loadingState.value = State.LOADED
        }
        enableEdgeToEdge()
        setContent {
            LaboralEXTheme {
                if (_loadingState.collectAsState().value == State.LOADING) LoadingScreen()
                else {
                    val userViewModel = hiltViewModel<UserViewModel>()
                    val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                    val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    val navController = rememberNavController()
                    Scaffold(bottomBar = { /*BottomNavigation()*/ }) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination =
                            if (user != null) NavigationManager.MainScreen
                            else NavigationManager.CreateUserScreen,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable<NavigationManager.MainScreen> {
                                MainScreen(navController, mainScreenViewModel)
                            }
                            composable<NavigationManager.CreateUserScreen> {
                                CreateUser(navController, userViewModel)
                            }
                            composable<NavigationManager.CreateCompanyScreen> {
                                CreateCompanyScreen(
                                    navController,
                                    createCompanyViewModel,
                                    insertCompaniesViewModel
                                )
                            }
                            composable<NavigationManager.InsertCompaniesScreen> {
                                InsertCompaniesScreen(navController, insertCompaniesViewModel)
                            }
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
                selected = _homeSelected.collectAsState().value,
                onClick = ::selectHome,
                label = { Text("Inicio") },
                icon = { Icon(Icons.Default.Home, contentDescription = null) }
            )
            NavigationBarItem(
                selected = _companiesSelected.collectAsState().value,
                onClick = ::selectCompanies,
                label = { Text("Compañías") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) }
            )
            NavigationBarItem(
                selected = _skillsSelected.collectAsState().value,
                onClick = ::selectSkills,
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