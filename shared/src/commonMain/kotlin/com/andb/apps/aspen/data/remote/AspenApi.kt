package com.andb.apps.aspen.data.remote

import com.andb.apps.aspen.response.CheckLoginResponse
import com.andb.apps.aspen.response.CourseListResponse
import com.andb.apps.aspen.response.RecentResponse
import com.andb.apps.aspen.util.result.SuspendableResult

interface AspenApi {
    suspend fun checkLogin(username: String, password: String): SuspendableResult<CheckLoginResponse, Exception>
    suspend fun getSubjects(username: String, password: String, term: Int?): SuspendableResult<CourseListResponse, Exception>
    suspend fun getRecentAssignments(username: String, password: String): SuspendableResult<RecentResponse, Exception>
}