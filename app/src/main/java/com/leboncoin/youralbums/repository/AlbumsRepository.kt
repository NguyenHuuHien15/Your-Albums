package com.leboncoin.youralbums.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.leboncoin.youralbums.network.AlbumNetwork
import com.leboncoin.youralbums.network.NetworkAlbumsContainer
import com.leboncoin.youralbums.network.asDatabaseModel
import com.leboncoin.youralbums.util.ClassHelper
import com.leboncoin.youralbums.database.AlbumsDatabase
import com.leboncoin.youralbums.database.asDomainModel
import com.leboncoin.youralbums.domain.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching albums from the network and storing them on disk
 */
class AlbumsRepository(private val database: AlbumsDatabase) {
    val LOG_TAG = AlbumsRepository::class.java.simpleName

    val albums: LiveData<List<Album>> = Transformations.map(database.albumDao.getAlbums()) {
        it.asDomainModel()
    }

    /**
     * Refresh the albums stored in the offline cache.
     */
    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            val albums = AlbumNetwork.albums.getData()
            albums.forEach {
                ClassHelper.toString(it)?.let { it1 -> Log.d(LOG_TAG, it1) }
            }
            val container = NetworkAlbumsContainer(albums)
            database.albumDao.insertAll(container.asDatabaseModel())
        }
    }
}
