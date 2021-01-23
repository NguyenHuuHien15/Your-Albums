package com.leboncoin.youralbums.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * A retrofit service to fetch albums.
 */

private const val BASE_URL = "https://static.leboncoin.fr/img/shared/"

interface AlbumService {
    @GET("technical-test.json")
    suspend fun getData(): List<NetworkAlbum>
}

/**
 * Main entry point for network access
 */
object AlbumNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val albums = retrofit.create(AlbumService::class.java)
}


