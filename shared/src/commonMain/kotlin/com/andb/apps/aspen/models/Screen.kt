package com.andb.apps.aspen.models

sealed class Screen {
    object Login : Screen()
    class Subjects() : Screen()
    class Assignments(val subject: Subject) : Screen()
}