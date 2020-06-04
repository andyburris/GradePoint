package com.andb.apps.aspen.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(val name: String, val weight: String, val average: String, val letter: String)