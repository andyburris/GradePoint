package com.andb.apps.aspen.state

import com.andb.apps.aspen.models.Assignment
import com.andb.apps.aspen.models.HomeTab
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.Term
import com.andb.apps.aspen.response.RecentAssignmentResponse
import com.andb.apps.aspen.util.replace

fun List<Subject>.combineWith(other: List<Subject>): List<Subject>{
    val allSubjects = this.toMutableList()
    val (modifiedSubjects, newSubjects) = other.partition { otherSubject -> this.any { it.id == otherSubject.id } }
    for (subject in modifiedSubjects){
        val combined = allSubjects.first { it.id == subject.id } + subject
        allSubjects.replace(combined) { it.id == subject.id }
    }
    for (newSubject in newSubjects){

        val index = other.indexOf(newSubject)
        val before = other.getOrNull(index - 1)
        if (before == null){
            allSubjects.add(0, newSubject)
            continue
        }

        val allIndex = allSubjects.indexOfFirst { it.id == before.id } + 1
        allSubjects.add(allIndex, newSubject)
    }

    return allSubjects
}