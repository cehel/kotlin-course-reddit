package ch.zuehlke.reddit.features.news

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ch.zuehlke.reddit.data.source.RedditRepository

/**
 * Created by celineheldner on 28.02.18.
 */
class NewsViewModelFactory(private val redditRepository: RedditRepository): ViewModelProvider.Factory{



    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(redditRepository) as T
        }
        throw IllegalArgumentException("Unkown Viewmodel class $modelClass cannot initiate it.")
    }


}