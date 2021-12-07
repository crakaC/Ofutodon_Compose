package com.crakac.ofutodon.mastodon.params

import retrofit2.http.Query

data class PageQuery(
    @Query("max_id")
    val maxId: Long,
    @Query("min_id")
    val minId: Long,
    @Query("since_id")
    val sinceId: Long,
    @Query("limit")
    val limit: Int,
    @Query("offset")
    val offset: Int
)
