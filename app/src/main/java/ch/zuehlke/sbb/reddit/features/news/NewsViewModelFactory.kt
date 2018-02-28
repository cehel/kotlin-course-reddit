package ch.zuehlke.sbb.reddit.features.news

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ch.zuehlke.sbb.reddit.data.source.RedditRepository

/**
 * Created by celineheldner on 28.02.18.
 */
class NewsViewModelFactory(private val redditRepository: RedditRepository): ViewModelProvider.Factory{



    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(redditRepository) as T
        }
        throw IllegalArgumentException()
    }


}