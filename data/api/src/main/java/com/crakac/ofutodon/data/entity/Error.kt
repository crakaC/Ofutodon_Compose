package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("error")
    val error: String = "",
    @SerializedName("error_description")
    val errorDescription: String = "",
)
