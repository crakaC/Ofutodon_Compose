package com.crakac.ofutodon.mastodon.api

import com.crakac.ofutodon.mastodon.entity.*
import com.crakac.ofutodon.mastodon.entity.Tag
import com.crakac.ofutodon.mastodon.params.AccountCredentials
import retrofit2.http.*
import retrofit2.http.Field

interface AccountsResources {

    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(
        @Path("id")
        accountId: Long
    ): Account

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun getCurrentAccount(): Account

    @FormUrlEncoded
    @PATCH("/api/v1/accounts/update_credentials")
    suspend fun updateAccountCredentials(
        @Body
        credentials: AccountCredentials
    )

    @GET("/api/v1/accounts/{id}/followers")
    suspend fun getFollowers(
        @Path("id")
        accountId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @GET("/api/v1/accounts/{id}/following")
    suspend fun getFollowings(
        @Path("id")
        accountId: Long,
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @GET("/api/v1/accounts/{id}/statuses")
    suspend fun getStatuses(
        @Path("id")
        accountId: Long,
        @Query("only_media")
        onlyMedia: Boolean? = null,
        @Query("exclude_replies")
        excludeReplies: Boolean? = null,
        @Query("exclude_reblogs")
        excludeReblogs: Boolean? = null,
//        @Query("tagged") // Added in v2.8.0
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Status>

    @GET("/api/v1/accounts/{id}/featured_tags")
    suspend fun getTagsFeaturedBySpecificAccount(
        @Path("id")
        accountId: Long
    ): List<Tag>

    @GET("/api/v1/accounts/{id}/lists")
    suspend fun getUserListByAccount(
        @Path("id")
        accountId: Long
    ): List<UserList>

    @GET("/api/v1/accounts/{id}/identity_proofs")
    suspend fun getIdentifyProof(
        @Path("id")
        accountId: Long
    ): List<IdentifyProof>

    @FormUrlEncoded
    @POST("/api/v1/accounts/{id}/follow")
    suspend fun follow(
        @Path("id")
        accountId: Long,
        @Field("reblogs")
        receiveReblogs: Boolean? = null,
        @Field("notify")
        receiveNotifications: Boolean? = null
    ): Relationship

    @POST("/api/v1/accounts/{id}/unfollow")
    suspend fun unfollow(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/block")
    suspend fun block(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/unblock")
    suspend fun unblock(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/mute")
    suspend fun mute(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/unmute")
    suspend fun unmute(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/pin")
    suspend fun addAccountToFeaturedProfile(
        @Path("id")
        accountId: Long
    ): Relationship

    @POST("/api/v1/accounts/{id}/unpin")
    suspend fun removeAccountFromFeaturedProfile(
        @Path("id")
        accountId: Long
    ): Relationship

    @FormUrlEncoded
    @POST("/api/v1/accounts/{id}/note")
    suspend fun setNoteOnUser(
        @Path("id")
        accountId: Long,
        @Field("comment")
        comment: String
    ): Relationship

    @GET("/api/v1/accounts/relationships")
    suspend fun getRelationships(
        @Query("id")
        accountId: Long
    ): List<Relationship>

    @GET("/api/v1/accounts/search")
    suspend fun searchAccounts(
        @Query("q")
        query: String,
        @Query("limit")
        limit: Int? = null,
        @Query("resolve")
        resolveWebFingerLookup: Boolean? = null,
        @Query("following")
        onlyFollowing: Boolean? = null
    ): List<Account>

    @GET("/api/v1/bookmarks")
    suspend fun getBookmarks(
        @QueryMap
        pageQuery: Map<String, String>
    ): List<Status>

    @GET("/api/v1/favourites")
    suspend fun getFavourites(
        @QueryMap
        pageQuery: Map<String, String>
    ): List<Status>

    @GET("/api/v1/mutes")
    suspend fun getMutedAccounts(
        @QueryMap
        pageQuery: Map<String, String>
    ): List<Account>

    @GET("/api/v1/blocks")
    suspend fun getBlockedAccounts(
        @QueryMap
        pageQuery: Map<String, String>
    ): List<Account>

    @GET("/api/v1/domain_blocks")
    suspend fun getBlockedDomains(
        @QueryMap
        pageQuery: Map<String, String>
    ): List<String>

    @FormUrlEncoded
    @POST("/api/v1/domain_blocks")
    suspend fun blockDomain(
        @Field("domain")
        domain: String
    )

    @FormUrlEncoded
    @DELETE("/api/v1/domain_blocks")
    suspend fun unblockDomain(
        @Field("domain")
        domain: String
    )

    @GET("/api/v1/filters")
    suspend fun getAllFilters(): List<Filter>

    @GET("/api/v1/filters/{id}")
    suspend fun getFilter(
        @Path("id")
        filterId: Long
    ): Filter

    @FormUrlEncoded
    @POST("/api/v1/filters")
    suspend fun createFilter(
        @Field("phrase")
        phrase: String,
        @Field("context")
        context: List<String>,
        @Field("irreversible")
        irreversible: Boolean? = null,
        @Field("whole_word")
        wholeWord: Boolean? = null,
        @Field("expires_in")
        expiresInSeconds: Int? = null
    ): Filter

    @FormUrlEncoded
    @POST("/api/v1/filters/{id}")
    suspend fun updateFilter(
        @Path("id")
        filterId: Long,
        @Field("phrase")
        phrase: String,
        @Field("context")
        context: List<String>,
        @Field("irreversible")
        irreversible: Boolean? = null,
        @Field("whole_word")
        wholeWord: Boolean? = null,
        @Field("expires_in")
        expiresInSeconds: Int? = null
    ): Filter

    @DELETE("/api/v1/filters/{id}")
    suspend fun deleteFilter(
        @Path("id")
        filterId: Long
    )

    @FormUrlEncoded
    @POST("/api/v1/reports")
    suspend fun report(
        @Field("account_id")
        accountId: Long,
        @Field("status_ids")
        statusIds: List<Long>? = null,
        @Field("comment")
        comment: String? = null,
        @Field("forward")
        forwardToRemote: Boolean? = null
    ): Report

    @GET("/api/v1/follow_requests")
    suspend fun getFollowRequests(
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @FormUrlEncoded
    @POST("/api/v1/follow_requests/{id}/authorize")
    suspend fun acceptFollowRequest(
        @Path("id")
        accountId: Long
    ): Relationship

    @FormUrlEncoded
    @POST("/api/v1/follow_requests/{id}/reject")
    suspend fun rejectFollowRequest(
        @Path("id")
        accountId: Long
    ): Relationship

    @GET("/api/v1/endorsements")
    suspend fun getFeaturedProfiles(
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @GET("/api/v1/featured_tags")
    suspend fun getFeaturedTags(): List<FeaturedTag>

    @FormUrlEncoded
    @POST("/api/v1/featured_tags")
    suspend fun createFeaturedTag(
        @Field("name")
        tagName: String
    ): FeaturedTag

    @DELETE("/api/v1/featured_tags/{id}")
    suspend fun deleteFeaturedTag(
        @Path("id")
        tagId: Long
    )

    @GET("/api/v1/featured_tags/suggestions")
    suspend fun getSuggestedTagsToFeature(): List<Tag>

    @GET("/api/v1/preferences")
    suspend fun getPreferences(): Preferences

    @GET("/api/v1/suggestions")
    suspend fun getSuggestedAccount(
        @QueryMap
        pageQuery: Map<String, String> = emptyMap()
    ): List<Account>

    @DELETE("/api/v1/suggestions/{account_id}")
    suspend fun removeSuggestion(
        @Path("account_id")
        accountId: Long
    )

    @FormUrlEncoded
    @POST("/api/v1/follows")
    suspend fun followRemoteUser(
        @Field("uri")
        uri: String
    ): Account
}