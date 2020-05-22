package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val id: String,
    val name: String,
    val teacher: String,
    val term: String,
    val room: String,
    val currentTermGrade: String,
    val code: String,
    val postedGrades: HashMap<String, String>,
    val assignments: List<AssignmentResponse>
)