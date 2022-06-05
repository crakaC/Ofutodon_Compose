package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("replies_policy")
    val repliesPolicy: String = "",
) {
    enum class RepliesPolicy {
        @SerializedName("followed")
        Followed,
        @SerializedName("list")
        List,
        @SerializedName("none")
        None,
    }
}