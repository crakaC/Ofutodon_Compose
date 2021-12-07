package com.crakac.ofutodon.mastodon.api

import com.crakac.ofutodon.mastodon.entity.Results
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchResource {
    @GET("/api/v2/search")
    suspend fun search(
        @Query("q")
        query: String,
        @Query("account_id")
        accountId: Long? = null,
        @Query("type")
        type: String? = null,
        @Query("exclude_unreviewed")
        excludeUnreviewedTags: Boolean? = null,
        @Query("resolve")
        resolveWebFingerLookup: Boolean? = null,
        @Query("following")
        onlyFollowing: Boolean? = null,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): Results
}