package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("accounts")
    val accounts: String = "",
    @SerializedName("statuses")
    val statuses: String = "",
    @SerializedName("hashtags")
    val hashtags: String = "",
)
