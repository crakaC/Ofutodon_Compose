package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.di.MastodonModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MastodonApiTest {
    private val mastodon = MastodonModule.provideMastodon()

    @Test
    fun instanceIsNotNull() = runBlocking {
        val instance = mastodon.getInformation()
        Assert.assertNotNull(instance)
    }

    @Test
    fun getLocalTimeline() = runBlocking {
        val timeline = mastodon.getPublicTimeline()
        Assert.assertNotNull(timeline)
    }
}