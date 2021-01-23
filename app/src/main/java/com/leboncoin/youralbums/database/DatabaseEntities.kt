package com.leboncoin.youralbums.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leboncoin.youralbums.domain.Album

/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */

/**
 * DatabaseAlbum represents a album entity in the database.
 */
@Entity
data class DatabaseAlbum constructor(
    @PrimaryKey
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

/**
 * Map DatabaseAlbums to domain entities
 */
fun List<DatabaseAlbum>.asDomainModel(): List<Album> {
    return map {
        Album(
            id = it.id,
            albumId = it.albumId,
            title = it.title,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}
