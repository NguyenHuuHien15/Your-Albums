package com.leboncoin.youralbums.repository

import androidx.lifecycle.MutableLiveData
import com.leboncoin.youralbums.domain.Album

class FakeTestRepository() : IRepository {
    override val albums = MutableLiveData<List<Album>>()
    override suspend fun refreshAlbums() {
        //throw IOException()
    }

    fun addAlbum(album: Album) {
        val list = if (albums.value == null) mutableListOf<Album>() else albums.value!!.toMutableList()
        list.add(album)
        albums.value = list
    }
}