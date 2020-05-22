package com.andb.apps.aspen.data

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Grade
import com.andb.apps.aspen.models.Subject
import com.soywiz.klock.Date

object PlaceholderData {

    val assignments = listOf(
        Assignment(
            "0",
            "Test 3.2",
            "Assessments",
            Date(2020, 2, 24),
            Grade.Score(32.0, 36.0),
            "A"
        ),
        Assignment(
            "1",
            "Homework 2/23",
            "Practice and Application",
            Date(2020, 2, 23),
            Grade.Score(8.5, 10.0),
            "A"
        ),
        Assignment(
            "2",
            "Derivatives Worksheet",
            "Practice and Application",
            Date(2020, 2, 22),
            Grade.Score(9.0, 10.0),
            "A"
        ),
        Assignment(
            "3",
            "Homework 2/22",
            "Participation",
            Date(2020, 2, 22),
            Grade.Score(10.0, 10.0),
            "A"
        )
    )

    val subjects = listOf(
        Subject(
            "0",
            "AP Calculus BC",
            "Walker Yane",
            Subject.Config("0", Subject.Icon.CALCULUS, 0xFF56CCF2.toInt()),
            Grade.Letter(93.85, "A"),
            assignments
        ),
        Subject(
            "1",
            "AP US History",
            "Matthew Burgoyne",
            Subject.Config("1", Subject.Icon.SCHOOL, 0xFFF2994A.toInt()),
            Grade.Letter(95.13, "A"),
            assignments
        ),
        Subject(
            "2",
            "Pre-AP French",
            "Anna Foxen",
            Subject.Config("2", Subject.Icon.LANGUAGE, 0xFF27AE60.toInt()),
            Grade.Letter(99.67, "A"),
            assignments
        ),
        Subject(
            "3",
            "AP Chemistry",
            "Will Gomaa",
            Subject.Config("3", Subject.Icon.FLASK, 0xFFF2C94C.toInt()),
            Grade.Letter(109.38, "A"),
            assignments
        ),
        Subject(
            "4",
            "AP Computer Science A",
            "Alex Jacoby",
            Subject.Config("4", Subject.Icon.COMPUTER, 0xFFE0E0E0.toInt()),
            Grade.Letter(99.0, "A"),
            assignments
        ),
        Subject(
            "5",
            "Digital Media",
            "Alexandra Stryker",
            Subject.Config("5", Subject.Icon.ART, 0xFF9B51E0.toInt()),
            Grade.Letter(99.14, "A"),
            assignments
        ),
        Subject(
            "6",
            "AP English Language",
            "Jennifer McLaughlin",
            Subject.Config("6", Subject.Icon.BOOK, 0xFFEB5757.toInt()),
            Grade.Letter(57.14, "F"),
            assignments
        )
    )
}