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
import com.example.laboralex.ui.screens.company.CreateCompanyScreen
import com.example.laboralex.ui.screens.company.InsertCompaniesScreen
import com.example.laboralex.ui.screens.curriculum.CurriculumEditor
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.screens.user.CreateUser
import com.example.laboralex.ui.screens.user.UserScreen
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CreateCompanyViewModel
import com.example.laboralex.viewmodel.CurriculumEditorViewModel
import com.example.laboralex.viewmodel.InsertCompaniesViewModel
import com.example.laboralex.viewmodel.MainScreenViewModel
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
                val insertCompaniesViewModel = hiltViewModel<InsertCompaniesViewModel>()
                val createCompanyViewModel = hiltViewModel<CreateCompanyViewModel>()
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                val curriculumEditorViewModel = hiltViewModel<CurriculumEditorViewModel>()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationManager.CreateUserScreen,
                    modifier = Modifier.padding(25.dp)
                ) {
                    composable<NavigationManager.MainScreen> {
                        MainScreen(navController, mainScreenViewModel)
                    }
                    composable<NavigationManager.UserScreen> {
                        UserScreen(navController, userViewModel)
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
                    composable<NavigationManager.CurriculumEditor> {
                        CurriculumEditor(curriculumEditorViewModel)
                    }
                }
            }
        }
    }


}