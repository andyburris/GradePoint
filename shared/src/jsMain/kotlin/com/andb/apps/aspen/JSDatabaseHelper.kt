package com.andb.apps.aspen

import com.andb.apps.aspen.db.SubjectConfig
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList
import kotlinx.serialization.builtins.ListSerializer
import org.w3c.dom.get
import org.w3c.dom.set
import kotlinx.browser.localStorage
import kotlinx.browser.sessionStorage
import kotlinx.serialization.builtins.list
import kotlinx.serialization.list
import kotlin.js.iterator

class JSDatabaseHelper : DatabaseHelper {

    val json = Json.Default

    override fun selectAllItems(): List<SubjectConfig> {
        val serialized = localStorage[STORAGE_KEY] ?: return emptyList()
        return json.parseList<Subject.Config>(serialized).map { SubjectConfig.Impl(it.id, it.icon.name, it.color.toLong()) }
    }

    override fun selectAllItemsByIds(ids: List<String>): List<SubjectConfig> {
        return selectAllItems().filter { it.id in ids }
    }

    override suspend fun insertSubjectConfigs(configs: List<Subject.Config>) {
        val currentList = selectAllItems().map { it.toConfig() }
        val newList = currentList + configs
        localStorage[STORAGE_KEY] = json.encodeToString(ListSerializer(Subject.Config.serializer()), value = newList)
    }

    override suspend fun upsertSubjectConfig(config: Subject.Config) {
        insertSubjectConfigs(listOf(config))
    }

    override suspend fun selectById(id: String): SubjectConfig? {
        return selectAllItems().find { it.id == id }
    }

    override suspend fun deleteAll() {
        sessionStorage.removeItem(STORAGE_KEY)
    }

    companion object {
        private const val STORAGE_KEY = "subjectConfig"
    }

}