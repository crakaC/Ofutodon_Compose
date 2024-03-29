package com.crakac.ofutodon.data.params

import com.crakac.ofutodon.data.entity.Poll
import com.crakac.ofutodon.data.entity.Status
import com.google.gson.annotations.SerializedName

data class StatusBody(
    @SerializedName("status")
    var content: String? = null,
    @SerializedName("media_ids")
    var mediaIds: List<Long>? = null,
    @SerializedName("poll")
    var poll: Poll? = null,
    @SerializedName("in_reply_to_id")
    var inReplyToId: Long? = null,
    @SerializedName("sensitive")
    var isSensitive: Boolean? = null,
    @SerializedName("spoiler_text")
    var spoilerText: String? = null,
    @SerializedName("visibility")
    var visibility: Status.Visibility? = null,
    @SerializedName("scheduled_at")
    var scheduledAt: String? = null,
    @SerializedName("language")
    var language: String? = null,
)
