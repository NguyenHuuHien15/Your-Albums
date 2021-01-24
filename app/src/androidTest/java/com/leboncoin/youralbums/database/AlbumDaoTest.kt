package com.leboncoin.youralbums.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlbumDaoTest {
    private lateinit var database: AlbumsDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumsDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertAll() = runBlockingTest {
        // GIVEN - insert albums
        val albums = mutableListOf<DatabaseAlbum>()
        albums.add(DatabaseAlbum(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        albums.add(DatabaseAlbum(2, 22, "B2", "https://bbbb", "https://bbbbb"))
        database.albumDao.insertAll(albums)

        // WHEN - get albums from database
        val albumsFromDb = database.albumDao.getAlbums().value

        // THEN - The loaded data contains the expected values
        assertThat(albumsFromDb, notNullValue())
        assertThat(albumsFromDb!!.isNotEmpty(), `is`(true))
    }
}