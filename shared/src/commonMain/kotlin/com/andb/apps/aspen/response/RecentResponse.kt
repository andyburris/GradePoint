package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class RecentResponse(
    val errors: ErrorResponse,
    val data: List<RecentAssignmentResponse>,
    val asOf: Int
)