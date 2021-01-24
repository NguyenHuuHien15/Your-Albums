package com.leboncoin.youralbums.repository

import androidx.lifecycle.LiveData
import com.leboncoin.youralbums.domain.Album

interface IRepository {
    val albums: LiveData<List<Album>>

    /**
     * Refresh the albums stored in the offline cache.
     */
    suspend fun refreshAlbums()
}