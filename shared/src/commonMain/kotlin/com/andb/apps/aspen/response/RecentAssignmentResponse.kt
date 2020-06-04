package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
class RecentAssignmentResponse (
    val id: String,
    val name: String,
    val course: String,
    val credit: String
)