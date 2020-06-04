package com.andb.apps.aspen

import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.toConfig
import kotlin.test.*

class SqlDelightTest : BaseTest() {

    private lateinit var dbHelper: DatabaseHelper


    @BeforeTest
    fun setup() = runTest {
        dbHelper = DatabaseHelper(testDbConnection())
        dbHelper.deleteAll()
        dbHelper.upsertSubjectConfig(Subject.Config("id", Subject.Icon.BOOK, 0xFFAAABAC.toInt()))
    }

    @Test
    fun `Select All Items Success`() = runTest {
        val configs = dbHelper.selectAllItems().executeAsList()
        assertNotNull(
            configs.find { it.id == "id" },
            "Could not retrieve Config"
        )
    }

    @Test
    fun `Select Item by Id Success`() = runTest {
        val configs = dbHelper.selectAllItems().executeAsList()
        val firstConfig = configs.first()
        assertNotNull(
            dbHelper.selectById(firstConfig.id).executeAsOneOrNull(),
            "Could not retrieve Config by Id"
        )
    }

    @Test
    fun `Update Item Success`() = runTest {
        val configs = dbHelper.selectAllItems().executeAsList()
        val firstConfig = configs.first().toConfig()
        dbHelper.upsertSubjectConfig(firstConfig.copy(icon = Subject.Icon.COMPASS, color = 0xFFABCDEF.toInt()))
        val newConfig = dbHelper.selectById(firstConfig.id).executeAsOneOrNull()
        assertNotNull(
            newConfig,
            "Could not retrieve Config by Id"
        )
        assertEquals(newConfig.iconName, Subject.Icon.COMPASS.name, "Favorite Did Not Save")
        assertEquals(newConfig.color, 0xFFABCDEF)
    }

    @Test
    fun `Delete All Success`() = runTest {
        dbHelper.upsertSubjectConfig(Subject.Config("id", Subject.Icon.BOOK, 0xFFAAABAC.toInt()))
        dbHelper.upsertSubjectConfig(Subject.Config("id2", Subject.Icon.FLASK, 0xFFABCDEF.toInt()))
        assertTrue(dbHelper.selectAllItems().executeAsList().isNotEmpty())
        dbHelper.deleteAll()
        assertTrue(
            dbHelper.selectAllItems().executeAsList().count() == 0,
            "Delete All did not work"
        )
    }
}