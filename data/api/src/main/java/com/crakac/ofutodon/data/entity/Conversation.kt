package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Conversation(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("accounts")
    val accounts: List<Account> = emptyList(),
    @SerializedName("unread")
    val unread: Boolean = false,
    @SerializedName("last_status")
    val lastStatus: Status = Status(),
)
