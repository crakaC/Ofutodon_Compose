package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.di.MastodonModule
import com.crakac.ofutodon.di.OkHttpModule
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.mastodon.params.PageQuery
import com.crakac.ofutodon.mastodon.params.StatusBody
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.junit.Assert
import org.junit.Test
import java.io.File

class MastodonApiTest {
    private val mastodon = MastodonModule.provideMastodon(
        OkHttpModule.provideOkHttp()
    )

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

    @Test
    fun getTimelineWithQuery() = runBlocking {
        val timeline = mastodon.getPublicTimeline(pageQuery = PageQuery(limit = 10).toMap())
        Assert.assertEquals("Query limit", 10, timeline.size)

        var query = PageQuery(maxId = timeline.last().id, limit = 10)
        val previous = mastodon.getPublicTimeline(pageQuery = query.toMap())
        Assert.assertTrue(
            "First item id of previous timeline should be less than current last status id",
            previous.first().id < timeline.last().id
        )

        query = PageQuery(sinceId = previous.first().id, limit = 10)
        val next =
            mastodon.getPublicTimeline(pageQuery = query.toMap())
        Assert.assertNotNull(next)
        Assert.assertTrue(
            "Last item id of next timeline should be greater than previous first status id",
            next.last().id > previous.first().id
        )
    }

    @Test
    fun attachmentTest() = runBlocking {
        val part = createMultipartBody("/thinking_face.png")
        // upload
        val attachment = mastodon.createAttachment(part, description = "create")
        Assert.assertNotNull(attachment)

        // update
        val updated =
            mastodon.updateAttachment(attachmentId = attachment.id, description = "update")
        Assert.assertNotEquals("different description", attachment.description, updated.description)

        val status = mastodon.postStatus(
            StatusBody(
                content = "🤔",
                mediaIds = listOf(attachment.id),
                visibility = Status.Visibility.Direct
            )
        )
        Assert.assertNotNull(status)

        val deleted = mastodon.deleteStatus(status.id)
        Assert.assertNotNull(deleted)
    }

    private fun createMultipartBody(filename: String): MultipartBody.Part {
        val file = File(javaClass.getResource(filename)!!.path)
        val body = file.asRequestBody()
        return MultipartBody.Part.createFormData("file", file.name, body)
    }
}