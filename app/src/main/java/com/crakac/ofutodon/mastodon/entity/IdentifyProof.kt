package com.crakac.ofutodon.mastodon.entity

import com.google.gson.annotations.SerializedName

data class IdentifyProof(
    @SerializedName("provider")
    val provider: String = "",
    @SerializedName("provider_username")
    val providerUsername: String = "",
    @SerializedName("profile_url")
    val profileUrl: String = "",
    @SerializedName("proof_url")
    val proofUrl: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
)
