package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("history")
    val history: List<History>? = null,
)

data class History(
    @SerializedName("day")
    val day: String = "",
    @SerializedName("uses")
    val uses: Long = 0L,
    @SerializedName("accounts")
    val accounts: Long = 0L,
)