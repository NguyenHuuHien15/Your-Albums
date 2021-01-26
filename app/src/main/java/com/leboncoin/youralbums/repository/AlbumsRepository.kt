package com.leboncoin.youralbums.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.leboncoin.youralbums.database.AlbumsDatabase
import com.leboncoin.youralbums.database.asDomainModel
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.network.AlbumNetwork
import com.leboncoin.youralbums.network.NetworkAlbumsContainer
import com.leboncoin.youralbums.network.asDatabaseModel
import com.leboncoin.youralbums.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Repository for fetching albums from the network and storing them on disk
 */
class AlbumsRepository(private val database: AlbumsDatabase) : IRepository {
    override val albums: LiveData<List<Album>> = wrapEspressoIdlingResource {
        Transformations.map(database.albumDao().getAlbums()) {
            it.asDomainModel()
        }
    }

    /**
     * Refresh the albums stored in the offline cache.
     */
    override suspend fun refreshAlbums() {
        wrapEspressoIdlingResource {
            try {
                val container = withContext(Dispatchers.IO) {
                    val albums = AlbumNetwork.albums.getData()
                    NetworkAlbumsContainer(albums)
                }
                database.albumDao().insertAll(container.asDatabaseModel())
            } catch (e: IOException) {
                // Error, so do not update, do nothing
            }
        }
    }
}
