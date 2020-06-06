package com.andb.apps.aspen

import com.andb.apps.aspen.data.remote.AspenApiMock
import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import com.russhwolf.settings.MockSettings
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AspenRepsitoryTest : BaseTest() {

    private lateinit var repository: AspenRepository
    private var dbHelper = DatabaseHelper(testDbConnection())
    private val settings = MockSettings()
    private val aspenApi = AspenApiMock()

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, aspenApi)
        dbHelper.deleteAll()

        repository = AspenRepository()
    }


    @Test
    fun saveSubjectConfigTest() = runTest {
        val (subjects, recents) = repository.getResponse()
        val subjectConfig = dbHelper.selectAllItems().executeAsList().first().toConfig()
        assertEquals(subjectConfig, subjects.first().config)
    }

    @Test
    fun useSavedSubjectConfigTest() = runTest {
        val savedConfig = Subject.Config("id", Subject.Icon.BOOK, 0xFFAAABAC.toInt())
        dbHelper.upsertSubjectConfig(savedConfig)
        val (subjects, recents) = repository.getResponse()
        assertEquals(subjects.first().config, savedConfig)
    }

    @Test
    fun updateSubjectConfigTest() = runTest {
        val (subjects, recents) = repository.getResponse()
        fun savedConfig() = dbHelper.selectAllItems().executeAsList().first().toConfig()

        val downloadedConfig = subjects.first().config
        assertEquals(savedConfig(), downloadedConfig)

        val iconChangeConfig = downloadedConfig.copy(icon = Subject.Icon.FLASK)
        dbHelper.upsertSubjectConfig(iconChangeConfig)
        assertEquals(savedConfig(), downloadedConfig)

        val colorChangeConfig = downloadedConfig.copy(color = 0xFF123456.toInt())
        dbHelper.upsertSubjectConfig(colorChangeConfig)
        assertEquals(savedConfig(), downloadedConfig)
    }

    @Test
    fun notifyErrorOnException() = runTest {
        aspenApi.throwOnRequest = true
        repository.getResponse()
        //TODO implement kotlin-result when it becomes multiplatform and check that error
    }

    @AfterTest
    fun breakdown() = runTest {
        dbHelper.deleteAll()
        appEnd()
    }
}

