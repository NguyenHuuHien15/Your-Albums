package com.leboncoin.youralbums.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<DatabaseAlbum>)

    @Query("select * from databasealbum")
    fun getAlbums(): LiveData<List<DatabaseAlbum>>
}