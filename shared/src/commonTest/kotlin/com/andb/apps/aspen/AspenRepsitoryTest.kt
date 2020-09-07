package com.andb.apps.aspen

import com.andb.apps.aspen.data.remote.AspenApiMock
import com.andb.apps.aspen.data.repository.AspenRepository
import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import com.netguru.kissme.Kissme
import kotlin.test.*

class AspenRepsitoryTest : BaseTest() {

    private lateinit var repository: AspenRepository
    private var dbHelper = DatabaseHelperImpl(testDbConnection())
    private val settings = Kissme()
    private val aspenApi = AspenApiMock()

    @BeforeTest
    fun setup() = runTest {
        appStart(dbHelper, settings, aspenApi)
        dbHelper.deleteAll()

        repository = AspenRepository()
    }


    @Test
    fun saveSubjectConfigTest() = runTest {
        val subjects = repository.getTerm(null)
        val subjectConfig = dbHelper.selectAllItems().first().toConfig()
        assertTrue(subjects.isSuccess)
        assertEquals(subjectConfig, subjects.get().first().config)
    }

    @Test
    fun useSavedSubjectConfigTest() = runTest {
        val savedConfig = Subject.Config("id", Subject.Icon.BOOK, 0xFFAAABAC.toInt(), false)
        dbHelper.upsertSubjectConfig(savedConfig)
        val subjects = repository.getTerm(null)
        assertTrue(subjects.isSuccess)
        assertEquals(subjects.get().first().config, savedConfig)
    }

    @Test
    fun updateSubjectConfigTest() = runTest {
        val subjects = repository.getTerm(null)
        fun savedConfig() = dbHelper.selectAllItems().first().toConfig()

        assertTrue(subjects.isSuccess)
        val downloadedConfig = subjects.get().first().config
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
        val subjects = repository.getTerm(null)
        assertTrue(subjects.isFailure)
    }

    @AfterTest
    fun breakdown() = runTest {
        dbHelper.deleteAll()
        appEnd()
    }
}

