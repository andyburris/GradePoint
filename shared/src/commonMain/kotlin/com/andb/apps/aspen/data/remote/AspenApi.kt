package com.andb.apps.aspen.data.remote

import com.andb.apps.aspen.response.CheckLoginResponse
import com.andb.apps.aspen.response.RecentResponse
import com.andb.apps.aspen.response.CourseListResponse

interface AspenApi {
    suspend fun checkLogin(username: String, password: String): CheckLoginResponse
    suspend fun getSubjects(username: String, password: String): CourseListResponse
    suspend fun getRecentAssignments(username: String, password: String): RecentResponse
}