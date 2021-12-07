package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Instance(
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("short_description")
    val shortDescription: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("version")
    val version: String = "",
    @SerializedName("languages")
    val languages: List<String> = emptyList(),
    @SerializedName("registrations")
    val registrations: Boolean = false,
    @SerializedName("approval_required")
    val approvalRequired: Boolean = false,
    @SerializedName("invites_enabled")
    val invitesEnabled: Boolean = false,
    @SerializedName("urls")
    val streamingApi: StreamingApi = StreamingApi(),
    @SerializedName("stats")
    val stats: Stats = Stats(),
    @SerializedName("user_count")
    val userCount: Long = 0L,
    @SerializedName("status_count")
    val statusCount: Long = 0L,
    @SerializedName("domain_count")
    val domainCount: Long = 0L,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("contact_account")
    val contactAccount: Account? = null,
) {
    data class StreamingApi(
        @SerializedName("streaming_api")
        val endpoint: String = ""
    )

    data class Stats(
        @SerializedName("user_count")
        val userCount: Long = 0L,

        @SerializedName("status_count")
        val statusCount: Long = 0L,

        @SerializedName("domain_count")
        val domainCount: Long = 0L
    )
}
