package com.example.laboralex.ui

import kotlinx.serialization.Serializable

class NavigationManager {

    @Serializable
    object AppRoot

    @Serializable
    object MainScreen

    @Serializable
    object CreateUserScreen

    @Serializable
    object UserSkills

    @Serializable
    object CoursesScreen

    @Serializable
    object CreateCourseScreen

    @Serializable
    object CreateSkill

    @Serializable
    object InsertCompaniesScreen

    @Serializable
    object InsertCompaniesScreenForm

    @Serializable
    object CreateCompanyScreen
}