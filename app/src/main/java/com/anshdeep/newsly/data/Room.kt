package com.anshdeep.newsly.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by ansh on 2019-07-09.
 */

@Dao
interface LatestNewsDao {
    @Query("select * from databaselatestnews  order by publishedAt desc")
    fun getLatestNews(): LiveData<List<DatabaseLatestNews>>

    @Query("select count(*) from databaselatestnews")
    suspend fun getLatestNewsSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLatestNews(vararg news: DatabaseLatestNews)

    @Query("delete from databaselatestnews")
    suspend fun clear()
}

@Database(entities = [DatabaseLatestNews::class], version = 2)
abstract class LatestNewsDatabase : RoomDatabase() {
    abstract val latestNewsDao: LatestNewsDao
}

private lateinit var INSTANCE: LatestNewsDatabase

fun getDatabase(context: Context): LatestNewsDatabase {
    synchronized(LatestNewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    LatestNewsDatabase::class.java,
                    "latestnews").build()
        }
    }
    return INSTANCE
}