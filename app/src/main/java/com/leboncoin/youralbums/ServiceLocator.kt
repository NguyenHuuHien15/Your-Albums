package com.leboncoin.youralbums

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.leboncoin.youralbums.database.AlbumsDatabase
import com.leboncoin.youralbums.repository.AlbumsRepository
import com.leboncoin.youralbums.repository.IRepository

object ServiceLocator {

    private val lock = Any()
    private var database: AlbumsDatabase? = null

    @Volatile
    var repository: IRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): IRepository {
        synchronized(this) {
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): IRepository {
        val database = database ?: createDataBase(context)
        val newRepo = AlbumsRepository(database)
        repository = newRepo
        return newRepo
    }

    private fun createDataBase(context: Context): AlbumsDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            AlbumsDatabase::class.java, "Albums.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }
}
