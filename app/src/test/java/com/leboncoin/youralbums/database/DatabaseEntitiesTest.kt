package com.leboncoin.youralbums.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DatabaseEntitiesTest {

    @Test
    fun asDomainModel_emptyDataInBDD_returnEmptyList() {
        val databaseAlbums = listOf<DatabaseAlbum>()
        val albums = databaseAlbums.asDomainModel()
        assertTrue(albums.isEmpty())
    }

    @Test
    fun asDomainModel_1DatabaseAlbum_return1Album() {
        val databaseAlbums = mutableListOf<DatabaseAlbum>()
        databaseAlbums.add(DatabaseAlbum(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        val albums = databaseAlbums.asDomainModel()
        assertTrue(albums.isNotEmpty())
        assertEquals(1, albums.size)
        val album = albums[0]
        assertEquals(1, album.id)
        assertEquals(11, album.albumId)
        assertEquals("A1", album.title)
        assertEquals("https://aaaa", album.url)
        assertEquals("https://aaaaa", album.thumbnailUrl)
    }

    @Test
    fun asDomainModel_2DatabaseAlbums_return2Albums() {
        val databaseAlbums = mutableListOf<DatabaseAlbum>()
        databaseAlbums.add(DatabaseAlbum(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        databaseAlbums.add(DatabaseAlbum(2, 22, "B2", "https://bbbb", "https://bbbbb"))
        val albums = databaseAlbums.asDomainModel()

        assertTrue(albums.isNotEmpty())
        assertEquals(2, albums.size)

        var album = albums[0]
        assertEquals(1, album.id)
        assertEquals(11, album.albumId)
        assertEquals("A1", album.title)
        assertEquals("https://aaaa", album.url)
        assertEquals("https://aaaaa", album.thumbnailUrl)

        album = albums[1]
        assertEquals(2, album.id)
        assertEquals(22, album.albumId)
        assertEquals("B2", album.title)
        assertEquals("https://bbbb", album.url)
        assertEquals("https://bbbbb", album.thumbnailUrl)
    }
}