package com.andb.apps.aspen.models

import com.andb.apps.aspen.db.SubjectConfig
import com.andb.apps.aspen.util.replace

data class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val config: Config,
    val terms: List<Term>
) {

    operator fun plus(other: Subject): Subject {
        val newTerms = this.terms.toMutableList()
        for (term in other.terms.filterIsInstance<Term.WithGrades>()){
            newTerms.replace(term) { it.term == term.term }
        }
        return this.copy(terms = newTerms)
    }

    fun hasTerm(term: Int): Boolean = terms.any { it.term==term && it is Term.WithGrades }
    fun termGrades(term: Int): Term.WithGrades = (terms.find { it.term==term } as? Term.WithGrades)
        ?: throw Error("Term $term does not exist for subject $name")

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