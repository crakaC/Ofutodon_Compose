package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("url")
    val url: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("author_name")
    val authorName: String = "",
    @SerializedName("author_url")
    val authorUrl: String = "",
    @SerializedName("provider_name")
    val providerName: String = "",
    @SerializedName("html")
    val html: String = "",
    @SerializedName("width")
    val width: Int = 0,
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("embed_url")
    val embedUrl: String = "",
    @SerializedName("blurhash")
    val blurhash: String = "",
) {
    enum class Type(val value: String) {
        Link("link"),
        Photo("photo"),
        Video("video"),
        Rich("rich")
    }
}