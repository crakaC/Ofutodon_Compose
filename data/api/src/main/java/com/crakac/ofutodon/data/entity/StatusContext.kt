package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class StatusContext(
    @SerializedName("ancestors")
    val ancestors: List<com.crakac.ofutodon.data.entity.Status> = emptyList(),
    @SerializedName("descendants")
    val descendants: List<com.crakac.ofutodon.data.entity.Status> = emptyList(),
)
