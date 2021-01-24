package com.leboncoin.youralbums.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private lateinit var INSTANCE: AlbumsDatabase

@Database(entities = [DatabaseAlbum::class], version = 1)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao
}

fun getDatabase(context: Context): AlbumsDatabase {
    synchronized(AlbumsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, AlbumsDatabase::class.java, "albums").build()
        }
    }
    return INSTANCE
}
