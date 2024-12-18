package com.example.laboralex.ui

import kotlinx.serialization.Serializable

class NavigationManager {
    @Serializable
    object MainScreen

    @Serializable
    object UserScreen

    @Serializable
    object CompanyScreen

    @Serializable
    object CreateUserScreen

    @Serializable
    object InsertCompaniesScreen

    @Serializable
    object CreateCompanyScreen
}