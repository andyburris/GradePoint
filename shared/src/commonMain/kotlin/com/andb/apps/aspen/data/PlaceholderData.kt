package com.andb.apps.aspen.data

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.Subject

object PlaceholderData {
    val subjects = listOf(
        Subject("0", "AP Calculus BC", "Walker Yane", Subject.Icon.CALCULUS, 0xFF56CCF2.toInt(), 93.85, 'A'),
        Subject("1", "AP US History", "Matthew Burgoyne", Subject.Icon.SCHOOL, 0xFFF2994A.toInt(), 95.13, 'A'),
        Subject("2", "Pre-AP French", "Anna Foxen", Subject.Icon.LANGUAGE, 0xFF27AE60.toInt(), 99.67, 'A'),
        Subject("3", "AP Chemistry", "Will Gomaa", Subject.Icon.FLASK, 0xFFF2C94C.toInt(), 109.38, 'A'),
        Subject("4", "AP Computer Science A", "Alex Jacoby", Subject.Icon.COMPUTER, 0xFFE0E0E0.toInt(), 99.0, 'A'),
        Subject("5", "Digital Media", "Alexandra Stryker", Subject.Icon.ART, 0xFF9B51E0.toInt(), 99.14, 'A'),
        Subject("6", "AP English Language", "Jennifer McLaughlin", Subject.Icon.BOOK, 0xFFEB5757.toInt(), 57.14, 'F')
    )

    val assignments = listOf(
        Assignment("Test 3.2", "Assessments", "Feb 24", 32.0, 36.0, 'A'),
        Assignment("Homework 2/23", "Practice and Application", "Feb 23", 8.5, 10.0, 'A'),
        Assignment("Derivatives Worksheet", "Practice and Application", "Feb 22", 9.0, 10.0, 'A'),
        Assignment("Homework 2/22", "Participation", "Feb 22", 10.0, 10.0, 'A')
    )
}