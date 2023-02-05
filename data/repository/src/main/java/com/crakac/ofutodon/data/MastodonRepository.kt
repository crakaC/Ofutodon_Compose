package com.crakac.ofutodon.data

import android.content.Context
import android.net.Uri
import com.crakac.ofutodon.data.entity.*
import com.crakac.ofutodon.data.params.PageQuery
import com.crakac.ofutodon.data.params.StatusBody
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ensureActive
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class MastodonRepository @Inject constructor(
    private val mastodon: Mastodon,
    @ApplicationContext context: Context,
) {
    private val contentResolver = context.contentResolver
    suspend fun getHomeTimeline(pageQuery: PageQuery = PageQuery()): List<Status> {
        return mastodon.getHomeTimeline(pageQuery = pageQuery.toMap())
    }

    suspend fun getPublicTimeline(
        localOnly: Boolean = false,
        pageQuery: PageQuery = PageQuery(),
    ): List<Status> {
        return mastodon.getPublicTimeline(localOnly = localOnly, pageQuery = pageQuery.toMap())
    }

    suspend fun postStatus(statusBody: StatusBody) =
        mastodon.postStatus(content = statusBody)

    suspend fun uploadMediaAttachments(uris: List<Uri>): List<Attachment> {
        val attachments = mutableListOf<Attachment>()
        for (uri in uris) {
            coroutineContext.ensureActive()
            val file =
                contentResolver.openInputStream(uri)?.use {
                    val mediaType = contentResolver.getType(uri)?.toMediaTypeOrNull()
                    val body = it.readBytes().toRequestBody(mediaType)
                    MultipartBody.Part.createFormData("file", "attachment", body)
                } ?: continue
            attachments += mastodon.createAttachment(file)
        }
        return attachments.toList()
    }

    suspend fun favourite(id: Long) = mastodon.favouriteStatus(id)

    suspend fun boost(id: Long) = mastodon.reblogStatus(id)
}
