package com.crakac.ofutodon.data.parameter

import com.google.gson.annotations.SerializedName

class FilterParam(
    @SerializedName("phrase")
    var phrase: String = "",
    @SerializedName("context")
    var context: List<String> = emptyList(),
    @SerializedName("expires_in")
    var expiresIn: Long? = null,
    @SerializedName("irreversible")
    var isIrreversible: Boolean? = null,
    @SerializedName("whole_word")
    var isWholeWord: Boolean? = null,
)
