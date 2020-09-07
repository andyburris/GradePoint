package com.andb.apps.aspen.data.repository

import co.touchlab.stately.ensureNeverFrozen
import com.andb.apps.aspen.DatabaseHelper
import com.andb.apps.aspen.Storage
import com.andb.apps.aspen.data.remote.AspenApi
import com.andb.apps.aspen.db.SubjectConfig
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import com.andb.apps.aspen.response.RecentAssignmentResponse
import com.andb.apps.aspen.response.toSubjectList
import com.andb.apps.aspen.util.result.SuspendableResult
import com.andb.apps.aspen.util.result.map
import com.andb.apps.aspen.util.result.onFailure
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

    suspend fun attemptLogin(username: String, password: String): SuspendableResult<Boolean, Exception> {
        return aspenApi.checkLogin(username, password).map {
            val correct = it.data
            if (correct) {
                storage.username = username
                storage.password = password
            }
            return@map correct
        }
    }

    suspend fun getTerm(term: Int?): SuspendableResult<List<Subject>, Exception> {
        val mapped = aspenApi.getSubjects(storage.username, storage.password, term).map { termResponse ->
            println("api success")
            val helper = dbHelper
            println("helper = $helper")
            var allItems: List<SubjectConfig> = emptyList()
            try {
                allItems = helper.selectAllItems()
            } catch (e: Exception){
                e.printStackTrace()
            }
            println("allItems = $allItems")
            val savedConfigs: List<Subject.Config> = /*dbHelper.selectAllItems()*/allItems.map { it.toConfig() }
            println("savedConfigs = $savedConfigs")
            val subjects = termResponse.toSubjectList(savedConfigs, term = term ?: termResponse.config.term.toInt())
            dbHelper.insertSubjectConfigs(subjects.map { it.config } - savedConfigs)
            return@map subjects
        }
        println("mapped = $mapped")
        mapped.onFailure {
            println(it.message)
            it.cause?.printStackTrace()
        }

        return mapped
    }

    suspend fun getRecentAssignments(): SuspendableResult<List<RecentAssignmentResponse>, Exception> {
        return aspenApi.getRecentAssignments(storage.username, storage.password).map {
            it.data
        }
    }

    fun logout() {
        storage.clearLogin()
    }

    fun updateSubjectConfig(config: Subject.Config){
        insertSubjectConfigs(listOf(config))
    }

    private fun insertSubjectConfigs(configs: List<Subject.Config>) = mainScope.launch {
        dbHelper.insertSubjectConfigs(configs)
    }

}