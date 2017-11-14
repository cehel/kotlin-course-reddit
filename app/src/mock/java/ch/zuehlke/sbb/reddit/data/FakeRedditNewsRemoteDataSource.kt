package ch.zuehlke.sbb.reddit.data

import com.google.common.collect.Lists

import java.util.LinkedHashMap

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.models.RedditNewsData

/**
 * Created by chsc on 08.11.17.
 */

class FakeRedditNewsRemoteDataSource// Prevent direct instantiation.
private constructor() : RedditDataSource {


    @Override
    fun getNews(@NonNull callback: LoadNewsCallback) {
        callback.onNewsLoaded(Lists.newArrayList(REDDIT_NEWS_SERVICE_DATA.values()))
    }

    @Override
    fun refreshNews() {
        // Not required because the {@link ch.zuehlke.sbb.reddit.data.source.RedditRepository} handles the logic of refreshing the
        // news from all the available data sources.
    }

    @Override
    fun deleteAllNews() {
        // Not supported by Reddit :)
    }

    @Override
    fun saveRedditNews(@NonNull data: RedditNewsData) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }

    companion object {

        private var INSTANCE: FakeRedditNewsRemoteDataSource? = null

        private val REDDIT_NEWS_SERVICE_DATA = LinkedHashMap()

        val instance: FakeRedditNewsRemoteDataSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = FakeRedditNewsRemoteDataSource()
                }
                return INSTANCE
            }
    }
}
