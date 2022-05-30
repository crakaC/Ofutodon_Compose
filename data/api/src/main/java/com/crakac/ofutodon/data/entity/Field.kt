package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Field(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("value")
    val value: String = "",
    @SerializedName("verified_at")
    val verifiedAt: String = "",
)