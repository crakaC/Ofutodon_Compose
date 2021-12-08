package com.crakac.ofutodon.mastodon.api

import com.crakac.ofutodon.mastodon.entity.*
import com.crakac.ofutodon.mastodon.params.StatusBody
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.http.Field

interface StatusesResources {
    @POST("/api/v1/statuses")
    suspend fun postStatus(
        @Body
        content: StatusBody
    ): Status

    @GET("/api/v1/statuses/{id}")
    suspend fun getStatus(
        @Path("id")
        statusId: Long
    ): Status

    @DELETE("/api/v1/statuses/{id}")
    suspend fun deleteStatus(
        @Path("id")
        statusId: Long
    ): Status

    @GET("/api/v1/statuses/{id}/context")
    suspend fun getStatusContext(
        @Path("id")
        statusId: Long
    ): StatusContext

    @GET("/api/v1/statuses/{id}/reblogged_by")
    suspend fun getRebloggedBy(
        @Path("id")
        statusId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @GET("/api/v1/statuses/{id}/favourited_by")
    suspend fun getFavouritedBy(
        @Path("id")
        statusId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @POST("/api/v1/statuses/{id}/favourite")
    suspend fun favouriteStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/unfavourite")
    suspend fun unfavouriteStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/reblog")
    suspend fun reblogStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/unreblog")
    suspend fun unreblogStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/bookmark")
    suspend fun bookmarkStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/unbookmark")
    suspend fun unbookmarkStatus(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/mute")
    suspend fun muteConversation(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/unmute")
    suspend fun unmuteConversation(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/pin")
    suspend fun pinToProfile(
        @Path("id")
        statusId: Long
    ): Status

    @POST("/api/v1/statuses/{id}/unpin")
    suspend fun unpinFromProfile(
        @Path("id")
        statusId: Long
    ): Status

    @Multipart
    @POST("/api/v1/media")
    /**
     * focus: Two floating points (x,y), comma-delimited, ranging from -1.0 to 1.0
     */
    suspend fun createAttachment(
        @Part("file")
        file: MultipartBody.Part,
        @Part("thumbnail")
        thumbnail: MultipartBody.Part? = null,
        @Part("description")
        description: String? = null,
        @Part("focus")
        focus: String? = null
    ): Attachment

    @GET("/api/v1/media/{id}")
    suspend fun getAttachment(
        @Path("id")
        attachmentId: Long
    ): Attachment

    @Multipart
    @PUT("/api/v1/media/{id}")
    suspend fun updateAttachment(
        @Path("id")
        attachmentId: Long,
        @Part("file")
        file: MultipartBody.Part,
        @Part("thumbnail")
        thumbnail: MultipartBody.Part? = null,
        @Part("description")
        description: String? = null,
        @Part("focus")
        focus: String? = null
    ): Attachment

    @GET("/api/v1/polls/{id}")
    suspend fun getPoll(
        @Path("id")
        pollId: Long
    ): Poll

    /**
     * choices: Array of own votes containing index for each option
     * (starting from 0)
     */
    @FormUrlEncoded
    @POST("/api/v1/polls/{id}/votes")
    suspend fun voteOnPoll(
        @Path("id")
        pollId: Long,
        @Field("choices")
        choices: List<Int>
    ): Poll
}