package com.crakac.ofutodon.data.entity

import com.google.gson.annotations.SerializedName

data class Emoji(
    @SerializedName("shortcode")
    val shortcode: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("static_url")
    val staticUrl: String = "",
    @SerializedName("visible_in_picker")
    val visibleInPicker: Boolean = false,
    @SerializedName("category")
    val category: String = "",
)