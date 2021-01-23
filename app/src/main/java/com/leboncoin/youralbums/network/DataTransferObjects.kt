package com.leboncoin.youralbums.network

import com.leboncoin.youralbums.database.DatabaseAlbum
import com.squareup.moshi.JsonClass

/**
 * AlbumsHolder holds a list of Albums.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "albums": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbumsContainer(val albums: List<NetworkAlbum>)

/**
 * Albums represent an album that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkAlbum(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkAlbumsContainer.asDatabaseModel(): List<DatabaseAlbum> {
    return albums.map {
        DatabaseAlbum(
            id = it.id,
            albumId = it.albumId,
            title = it.title,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}

