package com.andb.apps.aspen.data.remote

import com.andb.apps.aspen.models.Category
import com.andb.apps.aspen.response.*

class AspenApiMock : AspenApi {
    var jsonRequested = false
    var throwOnRequest = false

    override suspend fun checkLogin(username: String, password: String): CheckLoginResponse {
        if (throwOnRequest) throw Exception()
        jsonRequested = true
        return CheckLoginResponse(
            errors = ErrorResponse(0, null, null),
            data = username == "username" && password == "password",
            asOf = 0
        )
    }

    override suspend fun getSubjects(username: String, password: String): CourseListResponse {
        if (throwOnRequest) throw Exception()
        jsonRequested = true
        return courseListResponse
    }

    override suspend fun getRecentAssignments(username: String, password: String): RecentResponse {
        if (throwOnRequest) throw Exception()
        jsonRequested = true
        return recentResponse
    }
}

private val assignmentResponse = AssignmentResponse(
    id = "id",
    name = "Assignment Name",
    dateAssigned = "2/4/2020",
    dateDue = "2/5/2020",
    credit = "100%",
    feedback = null,
    score = "5.0",
    possibleScore = "5.0",
    gradeLetter = "A",
    category = "Category",
    stats = mapOf(
        "high" to "5.0 A",
        "low" to "0.0 F",
        "average" to "3.64 C",
        "median" to "5.0 A"
    )
)

private val courseResponse = CourseResponse(
    id = "id",
    name = "Course",
    teacher = "Name, Teacher",
    term = "FY",
    room = "A123",
    currentTermGrade = "92.35 A-",
    code = "ABC-123",
    postedGrades = hashMapOf(
        "1" to "A",
        "2" to "B",
        "3" to "C",
        "4" to "D"
    ),
    categoryTable = mapOf(
        "1" to listOf(Category("Homework", "10.0", "95.5", "A"), Category("Assessments", "50.0", "92.3", "A-"), Category("Practice and Application", "40.0", "94.8", "A")),
        "2" to listOf(Category("Homework", "10.0", "96.1", "A"), Category("Assessments", "50.0", "88.2", "B+"), Category("Practice and Application", "40.0", "91.8", "A")),
        "3" to listOf(Category("Homework", "10.0", "44.1", "F"), Category("Assessments", "50.0", "86.4", "B"), Category("Practice and Application", "40.0", "90.9", "A-")),
        "4" to listOf(Category("Homework", "10.0", "76.1", "C"), Category("Assessments", "50.0", "72.3", "C-"), Category("Practice and Application", "40.0", "83.2", "B"))
    ),
    assignments = listOf(
        assignmentResponse.copy(id = "0", name = "Test 1", category = "Assessments", score = "32.0", possibleScore = "36.0", credit = "88.9%", gradeLetter = "B+"),
        assignmentResponse.copy(id = "1", name = "Homework 2/4", category = "Homework"),
        assignmentResponse.copy(id = "2", name = "Homework 2/5", category = "Homework"),
        assignmentResponse.copy(id = "3", name = "Worksheet 2/5", category = "Practice and Application")
    )
)

private val courseListResponse = CourseListResponse(
    errors = ErrorResponse(0, null, null),
    data = listOf(
        courseResponse.copy(id = "0", name = "English", teacher = "Beasley, Finlay", currentTermGrade = "93.2 A"),
        courseResponse.copy(id = "1", name = "Pre-Calculus", teacher = "Michaelson, Lara", currentTermGrade = "85.7 B"),
        courseResponse.copy(id = "2", name = "Physics", teacher = "Seabrook, Wynona", currentTermGrade = "92.7 A"),
        courseResponse.copy(id = "3", name = "History", teacher = "Fiddler, Sasha", currentTermGrade = "94.8 A"),
        courseResponse.copy(id = "4", name = "French", teacher = "Tollemache, Louis", currentTermGrade = "97.1 A"),
        courseResponse.copy(id = "5", name = "Art", teacher = "Corvi, Eirini", currentTermGrade = "99.8 A"),
        courseResponse.copy(id = "6", name = "Psychology", teacher = "Wilkerson, Marcus", currentTermGrade = "84.6 B")
    ),
    asOf = 0
)

private val recentAssignmentResponse = RecentAssignmentResponse("id", "Assignment", "Course", "4")

private val recentResponse = RecentResponse(
    errors = ErrorResponse(0, null, null),
    data = listOf(
        recentAssignmentResponse.copy(id = "0", name = "Test 1", course = "English", credit = "32"),
        recentAssignmentResponse.copy(id = "1", name = "Homework 2/4", course = "Pre-Calculus", credit = "5"),
        recentAssignmentResponse.copy(id = "2", name = "Homework 2/5", course = "Physics", credit = "A"),
        recentAssignmentResponse.copy(id = "3", name = "Worksheet 2/5", course = "History", credit = "5")
    ),
    asOf = 0
)