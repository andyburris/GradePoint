package com.andb.apps.aspen.response

data class Assignment(
    val id: String,
    val name: String,
    val dateAssigned: String,
    val dateDue: String,
    val credit: String,
    val feedback: String?
) {
}