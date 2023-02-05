package com.crakac.ofutodon.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attachment(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("type")
    val type: Type = Type.Unknown,
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
    enum class Type {
        @SerializedName("unknown")
        Unknown,

        @SerializedName("image")
        Image,

        @SerializedName("video")
        Video,

        @SerializedName("gifv")
        Gifv,
    }
}
