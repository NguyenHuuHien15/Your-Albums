package com.leboncoin.youralbums.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leboncoin.youralbums.MainCoroutineRule
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.getOrAwaitValue
import com.leboncoin.youralbums.repository.FakeTestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumViewModelTest {

    private lateinit var viewModel: AlbumViewModel

    private lateinit var repository: FakeTestRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeTestRepository()
        repository.addAlbum(Album(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        viewModel = AlbumViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAlbums() {
        val albums = viewModel.albums.getOrAwaitValue()
        assertTrue(albums.isNotEmpty())
        assertEquals(1, albums.size)
    }

    @Test
    fun getEventNetworkError() {
        assertThat(viewModel.eventNetworkError.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun isNetworkErrorShown() {
        assertThat(viewModel.isNetworkErrorShown.getOrAwaitValue(), `is`(false))
    }
}