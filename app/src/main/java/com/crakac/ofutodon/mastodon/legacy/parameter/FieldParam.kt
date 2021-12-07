package com.crakac.ofutodon.mastodon.legacy.parameter

import com.google.gson.annotations.SerializedName

class FieldParam(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("value")
    val value: String = ""
)