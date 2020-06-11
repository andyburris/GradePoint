package com.andb.apps.aspen.models

import com.andb.apps.aspen.db.SubjectConfig

data class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val config: Config,
    val currentGrade: SubjectGrade,
    val categories: Map<String, List<Category>>,
    val assignments: List<Assignment>
) {
    enum class Icon() {
        ART,
        ATOM,
        BIOLOGY,
        BOOK,
        CALCULUS,
        CAMERA,
        COMPASS,
        COMPUTER,
        DICE,
        ECONOMICS,
        ENGINEERING,
        FILM,
        FINANCE,
        FLASK,
        FRENCH,
        GLOBE,
        GOVERNMENT,
        HEALTH,
        HISTORY,
        LANGUAGE,
        LAW,
        MUSIC,
        NEWS,
        PE,
        PSYCHOLOGY,
        SCHOOL,
        SOCIOLOGY,
        SPEAKING,
        STATISTICS,
        THEATER,
        TRANSLATE,
        WRITING,
    }

    data class Config(val id: String, val icon: Icon, val color: Int)

    companion object {
        val COLOR_PRESETS = listOf(
            0xFFEF5350,
            0xFFFB8C00,
            0xFFFFCA28,
            0xFF4CAF50,
            0xFF26C6DA,
            0xFF42A5F5,
            0xFF7E57C2,
            0xFFEC407A
        ).map { it.toInt() }
    }
}

fun SubjectConfig.toConfig() = Subject.Config(id, Subject.Icon.valueOf(iconName), color.toInt())