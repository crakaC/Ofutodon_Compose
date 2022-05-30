package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.data.entity.Status
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StatusTest {
    private lateinit var status: Status

    @Before
    fun setup() {
        status = loadStatus(javaClass)
    }

    @Test
    fun fromJson() {
        Assert.assertNotNull(status.account)
        Assert.assertNotNull(status.content)
    }
}

fun loadStatus(javaClass: Class<Any>): Status {
    val json = javaClass.getResourceAsStream("/status.json")?.use {
        it.reader().readText()
    }
    return GsonBuilder().create().fromJson(json, Status::class.java)
}