package com.spbstu.application

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.spbstu.application.api.FirebaseBuildingsApi
import com.spbstu.application.api.FirebaseHelpApi
import com.spbstu.application.api.FirebaseServicesApi
import com.spbstu.application.domain.model.ServiceCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.spbstu.application", appContext.packageName)
    }

    @Test
    fun buildingsTest() {
        FirebaseBuildingsApi.getBuildings {
            assertTrue(it.isNotEmpty())
            it.forEach { building ->
                FirebaseBuildingsApi.getBuildingActions(building.buildingId) { actions ->
                    assertTrue(actions.isNotEmpty())
                }
            }
        }
    }

    @Test
    fun helpTest() {
        FirebaseHelpApi.getHelp {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun servicesTest() {
        ServiceCategory.values().forEach {
            FirebaseServicesApi.getCategoryItems(it) { services ->
                assertTrue(services.isNotEmpty())
            }
        }
    }
}