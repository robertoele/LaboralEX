package com.example.laboralex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.laboralex.database.AppDatabase
import com.example.laboralex.database.entity.User
import com.example.laboralex.ui.screens.company.CompaniesList
import com.example.laboralex.ui.screens.company.CompanyScreen
import com.example.laboralex.ui.screens.main.MainScreen
import com.example.laboralex.ui.screens.user.UserScreen
import com.example.laboralex.ui.theme.LaboralEXTheme
import com.example.laboralex.viewmodel.CompanyViewModel
import com.example.laboralex.viewmodel.UserViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "laboralex.db").build()
    }

    private val userViewModel by viewModels<UserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(db.UserDao()) as T
            }
        }
    })

    private val companyViewModel by viewModels<CompanyViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CompanyViewModel(db.companyDao()) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaboralEXTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination =  MainScreen
                ) {
                    composable<MainScreen> {
                        MainScreen(navController, companyViewModel, userViewModel)
                    }
                    composable<UserScreen> {
                        UserScreen(navController, userViewModel)
                    }
                    composable<CompanyScreen> {
                        CompanyScreen(navController, companyViewModel)
                    }
                    composable<CompaniesScreen> {
                        CompaniesList(navController, companyViewModel)
                    }
                }
            }
        }
    }

    @Serializable
    object MainScreen
    @Serializable
    object UserScreen
    @Serializable
    object CompanyScreen
    @Serializable
    object CompaniesScreen
}