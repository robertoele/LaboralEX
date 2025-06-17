package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _loadingState = MutableStateFlow(State.LOADING)
    private val loadingState = _loadingState.asStateFlow()
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
                if (loadingState.collectAsState().value == State.LOADING) LoadingScreen()
                else {
                    val userViewModel = hiltViewModel<UserViewModel>()
                    val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                    val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination =
                        /*if (user != null) NavigationManager.MainScreen
                        else */NavigationManager.CreateUserScreen,
                        modifier = Modifier.padding(25.dp)
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