package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Preferences(
    @SerializedName("posting:default:visibility")
    val postingDefaultVisibility: String = "",
    @SerializedName("posting:default:sensitive")
    val postingDefaultSensitive: Boolean = false,
    @SerializedName("posting:default:language")
    val postingDefaultLanguage: String = "",
    @SerializedName("reading:expand:media")
    val readingExpandMedia: String = "",
    @SerializedName("reading:expand:spoilers")
    val readingExpandSpoilers: Boolean = false,
)
