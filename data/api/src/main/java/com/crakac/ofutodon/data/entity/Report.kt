package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("action_taken")
    val actionTaken: String = "",
)
