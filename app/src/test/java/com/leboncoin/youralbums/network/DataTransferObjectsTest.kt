package com.leboncoin.youralbums.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataTransferObjectsTest {

    @Test
    fun asDatabaseModel_emptyData_returnEmptyList() {
        val networkAlbums = listOf<NetworkAlbum>()
        val databaseAlbums = NetworkAlbumsContainer(networkAlbums).asDatabaseModel()
        assertTrue(databaseAlbums.isEmpty())
    }

    @Test
    fun asDatabaseModel_1Album_return1Album() {
        val networkAlbums = mutableListOf<NetworkAlbum>()
        networkAlbums.add(NetworkAlbum(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        val databaseAlbums = NetworkAlbumsContainer(networkAlbums).asDatabaseModel()
        assertTrue(databaseAlbums.isNotEmpty())
        assertEquals(1, databaseAlbums.size)
        val databaseAlbum = databaseAlbums[0]
        assertEquals(1, databaseAlbum.id)
        assertEquals(11, databaseAlbum.albumId)
        assertEquals("A1", databaseAlbum.title)
        assertEquals("https://aaaa", databaseAlbum.url)
        assertEquals("https://aaaaa", databaseAlbum.thumbnailUrl)
    }

    @Test
    fun asDatabaseModel_2Albums_return2Albums() {
        val networkAlbums = mutableListOf<NetworkAlbum>()
        networkAlbums.add(NetworkAlbum(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        networkAlbums.add(NetworkAlbum(2, 22, "B2", "https://bbbb", "https://bbbbb"))
        val databaseAlbums = NetworkAlbumsContainer(networkAlbums).asDatabaseModel()

        assertTrue(databaseAlbums.isNotEmpty())
        assertEquals(2, databaseAlbums.size)

        var databaseAlbum = databaseAlbums[0]
        assertEquals(1, databaseAlbum.id)
        assertEquals(11, databaseAlbum.albumId)
        assertEquals("A1", databaseAlbum.title)
        assertEquals("https://aaaa", databaseAlbum.url)
        assertEquals("https://aaaaa", databaseAlbum.thumbnailUrl)

        databaseAlbum = databaseAlbums[1]
        assertEquals(2, databaseAlbum.id)
        assertEquals(22, databaseAlbum.albumId)
        assertEquals("B2", databaseAlbum.title)
        assertEquals("https://bbbb", databaseAlbum.url)
        assertEquals("https://bbbbb", databaseAlbum.thumbnailUrl)
    }
}