package com.andb.apps.aspen.response

import com.andb.apps.aspen.models.Category
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
    val postedGrades: Map<String, String> = mapOf("1" to "", "2" to "", "3" to "", "4" to ""),
    val categoryTable: Map<String, List<Category>> = mapOf("1" to emptyList(), "2" to emptyList(), "3" to emptyList(), "4" to emptyList()),
    val assignments: List<AssignmentResponse> = emptyList()
)