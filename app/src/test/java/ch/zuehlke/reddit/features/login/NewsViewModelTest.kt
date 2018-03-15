package ch.zuehlke.reddit.features.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import ch.zuehlke.reddit.BaseTest
import ch.zuehlke.reddit.features.news.NewsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock


/**
 * Created by celineheldner on 09.03.18.
 */
class NewsViewModelTest: BaseTest(){

    @Mock lateinit var observer: Observer<NewsViewModel.ViewState>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    override fun setup(){
        super.setup()
        this.observer = mock()
        newsViewModel.viewState.observeForever(observer)

    }

    @Test
    fun testWrongCredentialsLogin(){

        newsViewModel.loadRedditNews(false,true)

        verify(observer,times(2)).onChanged(any())
    }

}