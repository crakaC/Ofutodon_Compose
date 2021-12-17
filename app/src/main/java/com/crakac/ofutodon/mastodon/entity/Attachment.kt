package com.crakac.ofutodon.mastodon.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attachment(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("type")
    val typeString: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("remote_url")
    val remoteUrl: String = "",
    @SerializedName("preview_url")
    val previewUrl: String = "",
    @SerializedName("text_url")
    val textUrl: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("blurhash")
    val blurhash: String = "",
) : Parcelable {
    enum class Type(val value: String) {
        Unknown("unknown"),
        Image("image"),
        Video("video"),
        Gifv("gifv")
    }

    @IgnoredOnParcel
    val type: Type by lazy {
        Type.values().first { e -> e.value == typeString }
    }
}