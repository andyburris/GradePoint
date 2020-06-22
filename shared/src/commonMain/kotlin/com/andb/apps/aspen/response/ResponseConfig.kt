package com.andb.apps.aspen.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseConfig(val district: String, val term: String)