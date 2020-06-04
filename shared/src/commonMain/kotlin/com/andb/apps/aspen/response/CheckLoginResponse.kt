package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckLoginResponse(
    val errors: ErrorResponse,
    val data: Boolean,
    val asOf: Int
)