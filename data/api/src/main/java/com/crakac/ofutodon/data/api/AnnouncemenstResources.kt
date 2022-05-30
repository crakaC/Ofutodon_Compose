package com.crakac.ofutodon.data.api

import com.crakac.ofutodon.data.entity.Announcement
import retrofit2.http.*

interface AnnouncemenstResources {

    @GET("/api/v1/announcements")
    suspend fun getAnnouncements(
        @Query("with_dismissed")
        includeDismissed: Boolean? = null
    ): List<com.crakac.ofutodon.data.entity.Announcement>

    @POST("/api/v1/announcements/{id}/dismiss")
    suspend fun dismissAnnouncement(
        @Path("id")
        announcementId: Long
    )

    /**
     * @param emoji Unicode emoji, or shortcode of custom emoji
     */
    @POST("/api/v1/announcements/{id}/reactions/{name}")
    suspend fun addReaction(
        @Path("id")
        announcementId: Long,
        @Path("name")
        emoji: String
    )

    /**
     * @param emoji Unicode emoji, or shortcode of custom emoji
     */
    @DELETE("/api/v1/announcements/{id}/reactions/{name}")
    suspend fun removeReaction(
        @Path("id")
        announcementId: Long,
        @Path("name")
        emoji: String
    )
}