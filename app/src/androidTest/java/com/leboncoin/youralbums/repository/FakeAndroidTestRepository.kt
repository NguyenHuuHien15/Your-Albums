package com.leboncoin.youralbums.repository

import androidx.lifecycle.MutableLiveData
import com.leboncoin.youralbums.domain.Album

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeAndroidTestRepository() : IRepository {

    override val albums = MutableLiveData<List<Album>>()
    override suspend fun refreshAlbums() {
    }

    fun addAlbum(album: Album) {
        val list = if (albums.value == null) mutableListOf() else albums.value!!.toMutableList()
        list.add(album)
        albums.value = list
    }
}
