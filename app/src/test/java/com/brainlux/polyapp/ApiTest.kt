package com.brainlux.polyapp

import com.brainlux.polyapp.api.*
import org.junit.Test

import org.junit.Assert.*


class ApiTest {

    @Test
    fun careersApiTest() {
        CareersApi.getCareersList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })
    }

    @Test
    fun clubsApiTest() {
        ClubsApi.getClubsList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })
    }

    @Test
    fun eatApiTest() {
        EatApi.getEatList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })
    }

    @Test
    fun enquiryApiTest() {
        EnquiryApi.getEnquiryList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })
    }

    @Test
    fun eventsApiTest() {
        EventsApi.getEventList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
            it.forEach { event ->
                EventsApi.getEventDescription(event.link, {
                    assertTrue(false)
                }) { description ->
                    assertTrue(description.isNotEmpty())
                }
            }
        })
    }

    @Test
    fun supportApiTest() {
        SupportApi.getSupportList({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })

        SupportApi.getFileLink({
            assertTrue(false)
        }, {
            assertTrue(it.isNotEmpty())
        })
    }
}