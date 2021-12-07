package com.crakac.ofutodon.mastodon.api

import com.crakac.ofutodon.mastodon.entity.*
import retrofit2.http.GET
import retrofit2.http.Query

interface InstanceResource {
    @GET("/api/v1/instance")
    suspend fun getInformation(): Instance

    @GET("/api/v1/instance/peers")
    suspend fun getConnectedDomains(): List<String>

    @GET("/api/v1/instance/activity")
    suspend fun getWeeklyActivity(): List<Activity>

    @GET("/api/v1/trends")
    suspend fun getTrendingTags(
        @Query("limit")
        limit: Int = 10
    ): List<Tag>

    /**
     * @param offset How many accounts to skip before returning results.
     *        Default 0.
     * @param order "active" to sort by most recently posted statuses
     *        (default) or "new" to sort by most recently created profiles.
     */
    @GET("/api/v1/directory")
    suspend fun getAccountsInDirectory(
        @Query("offset")
        offset: Int? = null,
        @Query("limit")
        limit: Int = 40,
        @Query("order")
        order: String = "active",
        @Query("local")
        localOnly: Boolean? = null
    ): List<Account>

    @GET("/api/v1/custom_emojis")
    suspend fun getCustomEmojis(): List<Emoji>
}