package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class Application(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("website")
    val website: String = "",
    @SerializedName("vapid_key")
    val vapidKey: String = "",
    @SerializedName("client_id")
    val clientId: String = "",
    @SerializedName("client_secret")
    val clientSecret: String = "",
)