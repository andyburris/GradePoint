package com.andb.apps.aspen

import com.andb.apps.aspen.SubjectConfigDb
import com.andb.apps.aspen.SubjectConfig
import com.andb.apps.aspen.models.Subject
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DatabaseHelper {
    fun selectAllItems(): List<SubjectConfig>
    fun selectAllItemsByIds(ids: List<String>): List<SubjectConfig>
    suspend fun insertSubjectConfigs(configs: List<Subject.Config>)
    suspend fun upsertSubjectConfig(config: Subject.Config)
    suspend fun selectById(id: String): SubjectConfig?
    suspend fun deleteAll()
}

class DatabaseHelperImpl(sqlDriver: SqlDriver) : DatabaseHelper {
    private val dbRef: SubjectConfigDb = SubjectConfigDb(sqlDriver)

    override fun selectAllItems(): List<SubjectConfig> = dbRef.tableQueries.selectAll().executeAsList()

    override fun selectAllItemsByIds(ids: List<String>): List<SubjectConfig> = dbRef.tableQueries.selectAllByIds(ids).executeAsList()

    override suspend fun insertSubjectConfigs(configs: List<Subject.Config>) = withContext(Dispatchers.Default) {
        dbRef.transaction {
            configs.forEach { config ->
                dbRef.tableQueries.insertSubjectConfig(config.id, config.icon.name, config.color.toLong())
            }
        }
    }

    override suspend fun upsertSubjectConfig(config: Subject.Config) = withContext(Dispatchers.Default) {
        dbRef.tableQueries.insertSubjectConfig(config.id, config.icon.name, config.color.toLong())
    }

    override suspend fun selectById(id: String): SubjectConfig? =
        withContext(Dispatchers.Default) { dbRef.tableQueries.selectById(id).executeAsOneOrNull() }

    override suspend fun deleteAll() = withContext(Dispatchers.Default) { dbRef.tableQueries.deleteAll() }

}

internal fun Boolean.toLong(): Long = if (this) 1L else 0L