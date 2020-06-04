package com.andb.apps.aspen

import com.andb.apps.aspen.db.KampstarterDb
import com.andb.apps.aspen.db.SubjectConfig
import com.andb.apps.aspen.models.Subject
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(sqlDriver: SqlDriver) {
    private val dbRef: KampstarterDb = KampstarterDb(sqlDriver)

    fun selectAllItems(): Query<SubjectConfig> = dbRef.tableQueries.selectAll()

    fun selectAllItemsByIds(ids: List<String>): Query<SubjectConfig> = dbRef.tableQueries.selectAllByIds(ids)

    suspend fun insertSubjectConfigs(configs: List<Subject.Config>) = withContext(Dispatchers.Default) {
        dbRef.transaction {
            configs.forEach { config ->
                dbRef.tableQueries.insertSubjectConfig(config.id, config.icon.name, config.color.toLong())
            }
        }
    }

    suspend fun upsertSubjectConfig(config: Subject.Config) = withContext(Dispatchers.Default) {
        dbRef.tableQueries.insertSubjectConfig(config.id, config.icon.name, config.color.toLong())
    }

    suspend fun selectById(id: String): Query<SubjectConfig> =
        withContext(Dispatchers.Default) { dbRef.tableQueries.selectById(id) }

    suspend fun deleteAll() = withContext(Dispatchers.Default) { dbRef.tableQueries.deleteAll() }

}

internal fun Boolean.toLong(): Long = if (this) 1L else 0L