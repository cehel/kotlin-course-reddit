package ch.zuehlke.reddit.data.source

import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData

/**
 * Created by chsc on 08.11.17.
 */

interface RedditDataSource {

    interface LoadNewsCallback {

        fun onNewsLoaded(news: List<RedditNewsData>)

        fun onDataNotAvailable()
    }

    interface LoadPostsCallback {

        fun onPostsLoaded(posts: List<RedditPostsData>)

        fun onDataNotAvailable()
    }


    fun getMoreNews(callback: LoadNewsCallback)

    fun getNews(callback: LoadNewsCallback)

    fun getPosts(callback: LoadPostsCallback, permalink: String)

    fun savePosts(data: RedditPostsData)

    fun deletePostsWithPermaLink(permaLink: String)

    fun refreshNews()

    fun deleteAllNews()

    fun saveRedditNews(data: RedditNewsData)
}