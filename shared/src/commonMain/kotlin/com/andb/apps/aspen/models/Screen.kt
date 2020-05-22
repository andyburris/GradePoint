package com.andb.apps.aspen.models

sealed class Screen {
    object Login : Screen()
    sealed class Subjects : Screen(){
        object Loading : Subjects()
        data class List(val subjects: kotlin.collections.List<Subject>) : Subjects()
    }
    data class Assignments(val subject: Subject) : Screen()
}