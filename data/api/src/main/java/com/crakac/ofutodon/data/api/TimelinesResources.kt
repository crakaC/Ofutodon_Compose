package com.crakac.ofutodon.data.api

import com.crakac.ofutodon.data.entity.Account
import com.crakac.ofutodon.data.entity.Conversation
import com.crakac.ofutodon.data.entity.Status
import retrofit2.http.*

interface TimelinesResources {

    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(
        @Query("local")
        localOnly: Boolean? = null,
        @Query("remote")
        remoteOnly: Boolean? = null,
        @Query("only_media")
        onlyMedia: Boolean? = null,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): List<Status>

    @GET("/api/v1/timelines/tag/{hashtag}")
    suspend fun getHashtagTimeline(
        @Path("hashtag")
        tag: String,
        @Query("local")
        localOnly: Boolean? = null,
        @Query("only_media")
        onlyMedia: Boolean? = null,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): List<Status>

    @GET("/api/v1/timelines/home")
    suspend fun getHomeTimeline(
        @Query("local")
        localOnly: Boolean? = null,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): List<Status>

    @GET("/api/v1/timelines/list/{list_id}")
    suspend fun getUserListTimeline(
        @Path("list_id")
        listId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): List<Status>

    @GET("/api/v1/conversations")
    suspend fun getConversations(
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): Conversation

    @DELETE("/api/v1/conversations/{id}")
    suspend fun deleteConversation(
        @Path("id")
        conversationId: Long,
    )

    @POST("/api/v1/conversations/{id}/read")
    suspend fun markConversationAsRead(
        @Path("id")
        conversationId: Long,
    ): Conversation

    @GET("/api/v1/lists")
    suspend fun getUserLists(): List<com.crakac.ofutodon.data.entity.UserList>

    @GET("/api/v1/lists/{id}")
    suspend fun getUserList(
        @Path("id")
        listId: Long,
    ): com.crakac.ofutodon.data.entity.UserList

    @FormUrlEncoded
    @POST("/api/v1/lists")
    suspend fun createUserList(
        @Field("title")
        listTitle: String,
        @Field("replies_policy")
        repliesPolicy: String? = null,
    ): com.crakac.ofutodon.data.entity.UserList

    @FormUrlEncoded
    @PUT("/api/v1/lists/{id}")
    suspend fun updateUserList(
        @Path("id")
        listId: Long,
        @Field("title")
        listTitle: String? = null,
        @Field("replies_policy")
        repliesPolicy: String? = null,
    ): com.crakac.ofutodon.data.entity.UserList

    @DELETE("/api/v1/lists/{id}")
    suspend fun deleteUserList(
        @Path("id")
        listId: Long,
    )

    @GET("/api/v1/lists/{id}/accounts")
    suspend fun getAccountsInUserList(
        @Path("id")
        listId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap(),
    ): List<Account>

    @FormUrlEncoded
    @POST("/api/v1/lists/{id}/accounts")
    suspend fun addAccountsToUserList(
        @Path("id")
        listId: Long,
        @Field("account_ids")
        accountIds: List<Long>,
    )

    @FormUrlEncoded
    @DELETE("/api/v1/lists/{id}/accounts")
    suspend fun removeAccountsFromUserList(
        @Path("id")
        listId: Long,
        @Field("account_ids")
        accountIds: List<Long>,
    )

    @GET("/api/v1/markers")
    suspend fun getSavedTimelinePosition(
        @Query("timeline")
        timeline: List<String>,
    ): com.crakac.ofutodon.data.entity.Marker

    @FormUrlEncoded
    @POST("/api/v1/markers")
    suspend fun savePositionInTimeline(
        @Field("home[last_read_id]")
        homeLastReadId: Long? = null,
        @Field("notification[last_read_id]")
        notificationsLastReadId: Long? = null,
    ): com.crakac.ofutodon.data.entity.Marker
}
