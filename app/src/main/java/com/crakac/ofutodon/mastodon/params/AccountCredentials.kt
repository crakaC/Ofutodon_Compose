package com.crakac.ofutodon.mastodon.params

import com.crakac.ofutodon.mastodon.entity.Field
import com.google.gson.annotations.SerializedName

data class AccountCredentials(
    @SerializedName("discoverable")
    var isDiscoverable: Boolean? = null,
    @SerializedName("bot")
    var isBot: Boolean? = null,
    @SerializedName("display_name")
    var displayName: String? = null,
    @SerializedName("note")
    var note: String? = null,
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("header")
    var header: String? = null,
    @SerializedName("locked")
    var isLocked: Boolean? = null,
    @SerializedName("fields_attributes")
    var fieldsAttributes: List<Field>? = null,
    @SerializedName("source[privacy]")
    var defaultPrivacy: String? = null,
    @SerializedName("source[sensitive]")
    var defaultSensitive: Boolean? = null,
    @SerializedName("source[language]")
    var defaultLanguage: String? = null
)
