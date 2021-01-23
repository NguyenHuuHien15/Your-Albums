package com.leboncoin.youralbums.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlbumDao {
    @Query("select * from databasealbum")
    fun getVideos(): LiveData<List<DatabaseAlbum>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<DatabaseAlbum>)
}


@Database(entities = [DatabaseAlbum::class], version = 1)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao
}

private lateinit var INSTANCE: AlbumsDatabase

fun getDatabase(context: Context): AlbumsDatabase {
    synchronized(AlbumsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AlbumsDatabase::class.java,
                "albums"
            ).build()
        }
    }
    return INSTANCE
}
