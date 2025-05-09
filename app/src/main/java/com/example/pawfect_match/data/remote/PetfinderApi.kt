package com.example.pawfect_match.data.remote

import com.example.pawfect_match.data.model.AccessTokenResponse
import com.example.pawfect_match.data.model.AnimalResponse
import com.example.pawfect_match.data.model.SearchAnimalsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PetfinderApi {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse

    @GET("animals/{id}")
    suspend fun getAnimalById(
        @Path("id") id: String,
        @Header("Authorization") authHeader: String
    ): AnimalResponse

    @GET("animals")
    suspend fun searchAnimalsByType(
        @Query("type") type: String,
        @Header("Authorization") authHeader: String
    ): SearchAnimalsResponse

}
