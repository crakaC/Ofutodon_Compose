package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("phrase")
    val phrase: String = "",
    @SerializedName("context")
    val context: List<String> = emptyList(),
    @SerializedName("expires_at")
    val expiresAt: String = "",
    @SerializedName("irreversible")
    val irreversible: Boolean = false,
    @SerializedName("whole_word")
    val wholeWord: Boolean = false,
) {
    enum class FilterContext {
        @SerializedName("home")
        Home,

        @SerializedName("notifications")
        Notifications,

        @SerializedName("public")
        Public,

        @SerializedName("thread")
        Thread,
    }
}
