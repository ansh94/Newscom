package com.anshdeep.newsly

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.anshdeep.newsly.data.DatabaseLatestNews
import com.anshdeep.newsly.data.LatestNewsDao
import com.anshdeep.newsly.data.LatestNewsDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by ansh on 2019-08-01.
 */


/**
 * The recommended approach for testing your database implementation is writing a
 * JUnit test that runs on an Android device. Because these tests don't require
 * creating an activity, they should be faster to execute than your UI tests.
 */

@RunWith(AndroidJUnit4::class)
class LatestNewsDatabaseTests {

    private lateinit var latestNewsDao: LatestNewsDao
    private lateinit var db: LatestNewsDatabase


    // Dummy data
    private val news1 = DatabaseLatestNews("news1Url", "Test News 1",
            "news1Image", "news1time")
    private val news2 = DatabaseLatestNews("news2Url", "Test News 2",
            "news2Image", "news2time")

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, LatestNewsDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        latestNewsDao = db.latestNewsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetLatestNewsSize() = runBlocking {
        latestNewsDao.insertAllLatestNews(news1, news2)

        val latestNewsSize = latestNewsDao.getLatestNewsSize()

        assertEquals(latestNewsSize, 2)

    }


    @Test
    @Throws(Exception::class)
    fun insertAndClearDatabase() = runBlocking {
        latestNewsDao.insertAllLatestNews(news1, news2)

        val latestNewsSizeBefore = latestNewsDao.getLatestNewsSize()

        assertEquals(latestNewsSizeBefore, 2)

        latestNewsDao.clear()
        val latestNewsSizeAfter = latestNewsDao.getLatestNewsSize()

        assertEquals(latestNewsSizeAfter, 0)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNews() = runBlocking {
        latestNewsDao.insertAllLatestNews(news1, news2)

        val latestNews = LiveDataTestUtil.getValue(latestNewsDao.getLatestNews())

        assertEquals(latestNews.size, 2)
    }
}