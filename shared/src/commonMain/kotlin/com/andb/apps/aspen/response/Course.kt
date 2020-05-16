package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val name: String,
    val teacher: String,
    val term: String,
    val room: String,
    val currentTermGrade: String,
    val code: String
)