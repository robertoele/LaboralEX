package com.example.laboralex.ui

import kotlinx.serialization.Serializable

class NavigationManager {

    @Serializable
    data object AppRoot

    @Serializable
    data object MainScreen

    @Serializable
    data object CreateUserScreen

    @Serializable
    data object UserSkills

    @Serializable
    data object CoursesScreen

    @Serializable
    data object CreateCourseScreen

    @Serializable
    data object CreateSkill

    @Serializable
    data object InsertCompaniesScreen

    @Serializable
    data object InsertCompaniesScreenForm

    @Serializable
    data object CreateCompanyScreen
}