package com.leboncoin.youralbums.ui

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedDiagnosingMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.leboncoin.youralbums.R
import com.leboncoin.youralbums.ServiceLocator
import com.leboncoin.youralbums.domain.Album
import com.leboncoin.youralbums.repository.FakeAndroidTestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for the Albums List screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class AlbumsListFragmentTest {

    private lateinit var repository: FakeAndroidTestRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.repository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun verifyContent() = runBlockingTest {
        repository.addAlbum(Album(1, 11, "A1", "https://aaaa", "https://aaaaa"))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(ViewAssertions.matches(atPosition(0, ViewMatchers.withText("A1"))));
    }

    private fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedDiagnosingMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeMoreTo(description: Description?) {
                description?.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView?, mismatchDescription: Description?): Boolean {
                val viewHolder = view?.findViewHolderForAdapterPosition(position) ?: // has no item on such position
                return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

}
