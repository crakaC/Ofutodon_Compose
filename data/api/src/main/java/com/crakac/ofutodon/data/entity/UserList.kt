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
    class RepliesPolicy {
        companion object {
            val Followed = "Followed"
            val List = "list"
            val None = "none"
        }
    }
}