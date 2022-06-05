package com.crakac.ofutodon.data.api

import com.crakac.ofutodon.data.entity.Application
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppsResources {
    @FormUrlEncoded
    @POST("/api/v1/apps")
    suspend fun registerApplication(
        @Field("client_name")
        clientName: String,
        @Field("redirect_uris")
        redirectUris: String,
        @Field("scopes")
        scopes: String,
        @Field("website")
        webSite: String
    ): com.crakac.ofutodon.data.entity.AppCredentials

    @GET("/api/v1/apps/verify_credentials")
    suspend fun verifyCredentials(): Application

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun obtainToken(
        @Field("grant_type")
        grantType: String,
        @Field("client_id")
        clientId: String,
        @Field("client_secret")
        clientSecret: String,
        @Field("redirect_uri")
        redirectUrl: String,
        @Field("scope")
        scope: String,
        @Field("code")
        code: String
    ): com.crakac.ofutodon.data.entity.Token

    @FormUrlEncoded
    @POST("/oauth/revoke")
    suspend fun revokeToken(
        @Field("client_id")
        clientId: String,
        @Field("client_secret")
        clientSecret: String,
        @Field("token")
        accessToken: String
    )
}