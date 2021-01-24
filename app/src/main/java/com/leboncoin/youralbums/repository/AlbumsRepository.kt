package com.leboncoin.youralbums.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.leboncoin.youralbums.database.AlbumsDatabase
import com.leboncoin.youralbums.database.asDomainModel
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.network.AlbumNetwork
import com.leboncoin.youralbums.network.NetworkAlbumsContainer
import com.leboncoin.youralbums.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching albums from the network and storing them on disk
 */
class AlbumsRepository(private val database: AlbumsDatabase) : IRepository {
    override val albums: LiveData<List<Album>> = Transformations.map(database.albumDao.getAlbums()) {
        it.asDomainModel()
    }

    /**
     * Refresh the albums stored in the offline cache.
     */
    override suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            val albums = AlbumNetwork.albums.getData()
            val container = NetworkAlbumsContainer(albums)
            database.albumDao.insertAll(container.asDatabaseModel())
        }
    }
}
