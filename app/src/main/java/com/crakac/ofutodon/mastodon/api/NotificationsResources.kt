package com.crakac.ofutodon.mastodon.api

import com.crakac.ofutodon.mastodon.entity.Notification
import retrofit2.http.*

interface NotificationsResources {
    /**
     * @param excludeTypes: Array of types to exclude
     * (follow, favourite, reblog, mention, poll, follow_request)
     * @param accountId: Return only notifications received from this account
     */
    @GET("/api/v1/notifications")
    suspend fun getNotifications(
        @Query("exclude_types")
        excludeTypes: List<String>? = null,
        @Query("account_id")
        accountId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Notification>

    @GET("/api/v1/notifications/{id}")
    suspend fun getNotification(
        @Path("id")
        notificationId: Long
    ): Notification

    @POST("/api/v1/notifications/clear")
    suspend fun crearNotifications()

    @POST("/api/v1/notifications/{id}/dismiss")
    suspend fun dismissNotification(
        @Path("id")
        notificationId: Long
    )
}