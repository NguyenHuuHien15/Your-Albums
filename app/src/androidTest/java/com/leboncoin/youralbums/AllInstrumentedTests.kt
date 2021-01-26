package com.leboncoin.youralbums

import com.leboncoin.youralbums.database.AlbumDaoTest
import com.leboncoin.youralbums.ui.AlbumsListFragmentTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
// @formatter:off
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    AlbumDaoTest::class,
    AlbumsListFragmentTest::class
)
// @formatter:on
class AllInstrumentedTests {
}