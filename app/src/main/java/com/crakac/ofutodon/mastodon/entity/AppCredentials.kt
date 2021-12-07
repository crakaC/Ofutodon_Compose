package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName


data class AppCredentials(
    @SerializedName("client_id")
    val clientId: String = "",

    @SerializedName("client_secret")
    val clientSecret: String = "",

    @SerializedName("redirect_uri")
    val redirectUri: String = "",
)