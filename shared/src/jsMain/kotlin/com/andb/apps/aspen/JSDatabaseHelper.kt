package com.andb.apps.aspen

import com.andb.apps.aspen.db.SubjectConfig
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import com.squareup.sqldelight.Query
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parse
import kotlinx.serialization.parseList
import kotlinx.serialization.stringify
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.localStorage
import kotlin.browser.sessionStorage
import kotlin.js.iterator

@OptIn(ImplicitReflectionSerializer::class)
class JSDatabaseHelper : DatabaseHelper {

    val json = Json(JsonConfiguration.Default)

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
        localStorage[STORAGE_KEY] = json.stringify(newList)
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