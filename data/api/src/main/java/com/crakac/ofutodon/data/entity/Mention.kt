package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Mention(
    @SerializedName("url")
    val url: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("acct")
    val acct: String = "",
    @SerializedName("id")
    val id: Long = 0L,
)