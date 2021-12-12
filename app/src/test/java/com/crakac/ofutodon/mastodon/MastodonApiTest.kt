package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.di.MastodonModule
import com.crakac.ofutodon.di.OkHttpModule
import com.crakac.ofutodon.mastodon.entity.Status
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
    fun attachmentTest() = runBlocking {
        val part = createMultipartBody("/thinking_face.png")
        // upload
        val attachment = mastodon.createAttachment(part, description = "create")
        Assert.assertNotNull(attachment)

        // update
        val updated = mastodon.updateAttachment(attachmentId = attachment.id, description = "update")
        Assert.assertNotEquals("different description", attachment.description, updated.description)

        val status = mastodon.postStatus(StatusBody(content = "🤔", mediaIds = listOf(attachment.id), visibility = Status.Visibility.Direct))
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