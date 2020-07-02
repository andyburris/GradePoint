package com.andb.apps.aspen.data.repository

import co.touchlab.stately.ensureNeverFrozen
import com.andb.apps.aspen.DatabaseHelper
import com.andb.apps.aspen.Storage
import com.andb.apps.aspen.data.remote.AspenApi
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import com.andb.apps.aspen.response.RecentAssignmentResponse
import com.andb.apps.aspen.response.toSubjectList
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AspenRepository : BaseModel(), KoinComponent {

    private val aspenApi: AspenApi by inject()
    private val dbHelper: DatabaseHelper by inject()
    private val storage: Storage by inject()

    val loggedIn: Boolean get() = storage.loggedIn

    init {
        ensureNeverFrozen()
    }

    suspend fun attemptLogin(username: String, password: String): Boolean {
        val correct = aspenApi.checkLogin(username, password).data
        if (correct) {
            storage.username = username
            storage.password = password
        }
        return correct
    }

    suspend fun getTerm(term: Int?): List<Subject> {
        val termResponse = aspenApi.getSubjects(storage.username, storage.password, term)
        val savedConfigs: List<Subject.Config> = dbHelper.selectAllItems().executeAsList().map { it.toConfig() }
        val subjects = termResponse.toSubjectList(savedConfigs, term = term ?: termResponse.config.term.toInt())
        dbHelper.insertSubjectConfigs(subjects.map { it.config } - savedConfigs)
        return subjects
    }

    suspend fun getRecentAssignments(): List<RecentAssignmentResponse> {
        val recentResponse = aspenApi.getRecentAssignments(storage.username, storage.password)
        return recentResponse.data
    }

    fun logout() {
        storage.clear()
    }

    fun updateSubjectConfig(config: Subject.Config){
        insertSubjectConfigs(listOf(config))
    }

    private fun insertSubjectConfigs(configs: List<Subject.Config>) = mainScope.launch {
        dbHelper.insertSubjectConfigs(configs)
    }

}