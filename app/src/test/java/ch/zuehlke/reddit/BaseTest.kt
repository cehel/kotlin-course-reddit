package ch.zuehlke.reddit

import android.arch.core.executor.testing.InstantTaskExecutorRule
import ch.zuehlke.reddit.di.DaggerAppComponentTest
import ch.zuehlke.reddit.features.login.LoginViewModel
import ch.zuehlke.reddit.features.news.NewsViewModel
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Created by celineheldner on 09.03.18.
 */

open class BaseTest {

    /*
    TODO:chapter_3_testing_exercise
        Inject the LoginViewModel
        (As a Dagger Reminder: Look at the AppComponentTest - does it have the relevant Module connected?)
     */

    @Inject
    lateinit var newsViewModel: NewsViewModel

    @Before
    open fun setup(){
        DaggerAppComponentTest.builder().build().inject(this)
    }

}