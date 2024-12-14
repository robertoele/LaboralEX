package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.screens.company.CompaniesList
import com.example.laboralex.ui.screens.company.CompanyScreen
import com.example.laboralex.ui.screens.company.InsertCompaniesScreen
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.screens.user.CreateUser
import com.example.laboralex.ui.screens.user.UserScreen
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CompanyViewModel
import com.example.laboralex.viewmodel.SpecialityViewModel
import com.example.laboralex.viewmodel.UserSpecialityViewModel
import com.example.laboralex.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private enum class LoadingState { LOADING, LOADED }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaboralEXTheme {
                val userViewModel = hiltViewModel<UserViewModel>()
                val specialityViewModel = hiltViewModel<SpecialityViewModel>()
                val userSpecialityViewModel = hiltViewModel<UserSpecialityViewModel>()
                val companyViewModel = hiltViewModel<CompanyViewModel>()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationManager.CreateUserScreen,
                    modifier = Modifier.padding(25.dp)
                ) {
                    composable<NavigationManager.MainScreen> {
                        MainScreen(navController, companyViewModel, userViewModel)
                    }
                    composable<NavigationManager.UserScreen> {
                        UserScreen(navController, userViewModel)
                    }
                    composable<NavigationManager.CreateUserScreen> {
                        var loadingState by remember { mutableStateOf(LoadingState.LOADING) }
                        val specialities = remember { mutableStateListOf<Speciality>() }
                        
                        specialityViewModel.run {
                            viewModelScope.launch {
                                specialities.addAll(getSpecialities())
                                runOnUiThread { loadingState = LoadingState.LOADED }
                            }
                        }
                        if (loadingState == LoadingState.LOADING) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            CreateUser(
                                navController,
                                userViewModel,
                                specialityViewModel,
                                specialities,
                                this@MainActivity
                            )
                        }
                    }
                    composable<NavigationManager.InsertCompaniesScreen> {
                        InsertCompaniesScreen(navController, companyViewModel, specialityViewModel)
                    }
                    composable<NavigationManager.CompanyScreen> {
                        CompanyScreen(navController, companyViewModel)
                    }
                    composable<NavigationManager.CompaniesScreen> {
                        CompaniesList(navController, companyViewModel)
                    }
                }
            }
        }
    }


}