package com.leboncoin.youralbums

import com.leboncoin.youralbums.database.DatabaseEntitiesTest
import com.leboncoin.youralbums.viewmodels.AlbumViewModelTest
import org.junit.runner.RunWith

import org.junit.runners.Suite

@RunWith(Suite::class)
// @formatter:off
@Suite.SuiteClasses(
    ExampleUnitTest::class,
    DatabaseEntitiesTest::class,
    DatabaseEntitiesTest::class,
    AlbumViewModelTest::class
)
// @formatter:on
class AllTests {
}