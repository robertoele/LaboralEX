package com.example.laboralex.ui.screens

import com.example.laboralex.database.entity.Skill

data class SkillSelect(
    val skill: Skill,
    var selected: Boolean = false
) {
    fun changeSelected() {
        selected = !selected
    }
}
