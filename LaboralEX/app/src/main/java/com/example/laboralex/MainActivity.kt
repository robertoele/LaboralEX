package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboralex.ui.NavigationManager
import com.example.laboralex.ui.screens.company.CompaniesList
import com.example.laboralex.ui.screens.company.CompanyScreen
import com.example.laboralex.ui.screens.company.InsertCompaniesScreen
import com.example.laboralex.ui.screens.user.CreateUser
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.screens.user.UserScreen
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CompanyViewModel
import com.example.laboralex.viewmodel.SpecialityViewModel
import com.example.laboralex.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaboralEXTheme {
                val userViewModel = hiltViewModel<UserViewModel>()
                val specialityViewModel = hiltViewModel<SpecialityViewModel>()
                val companyViewModel = hiltViewModel<CompanyViewModel>()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination =  NavigationManager.CreateUserScreen,
                    modifier = Modifier.padding(25.dp)
                ) {
                    composable<NavigationManager.MainScreen> {
                        MainScreen(navController, companyViewModel, userViewModel)
                    }
                    composable<NavigationManager.UserScreen> {
                        UserScreen(navController, userViewModel)
                    }
                    composable<NavigationManager.CreateUserScreen> {
                        CreateUser(navController, userViewModel, specialityViewModel, this@MainActivity)
                    }
                    composable<NavigationManager.InsertCompaniesScreen> {
                        InsertCompaniesScreen(navController, companyViewModel)
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