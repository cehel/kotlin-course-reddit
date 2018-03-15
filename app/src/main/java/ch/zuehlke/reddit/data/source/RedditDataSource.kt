package ch.zuehlke.reddit.data.source

import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import io.reactivex.Flowable

/**
 * Created by chsc on 08.11.17.
 */

interface RedditDataSource {

    fun deleteAllNews()

    fun saveRedditNews(data: RedditNewsData)

    val news: Flowable<List<RedditNewsData>>

    interface LoadPostsCallback {

        fun onPostsLoaded(posts: List<RedditPostsData>)

        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback, permalink: String)

    fun savePosts(data: RedditPostsData)

    fun deletePostsWithPermaLink(permaLink: String)
}
