package com.crakac.ofutodon.data.parameter

import com.google.gson.annotations.SerializedName

class AccountCredentials(
    @SerializedName("display_name")
    var displayName: String? = null,
    @SerializedName("note")
    var note: String? = null,
    @SerializedName("locked")
    var isLocked: Boolean? = null,
    @SerializedName("source[privacy]")
    var defaultPrivacy: String? = null,
    @SerializedName("source[sensitive]")
    var defaultSensitive: Boolean? = null,
    @SerializedName("source[language]")
    var defaultLanguage: String? = null,
)
