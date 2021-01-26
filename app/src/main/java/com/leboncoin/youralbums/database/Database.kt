package com.leboncoin.youralbums.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAlbum::class], version = 1, exportSchema = false)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}
