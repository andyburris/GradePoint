package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class AspenResponse(
    val errors: ErrorResponse,
    val data: List<Course>,
    val asOf: Int
)

@Serializable
data class ErrorResponse(val id: Int, val title: String?, val details: String?)