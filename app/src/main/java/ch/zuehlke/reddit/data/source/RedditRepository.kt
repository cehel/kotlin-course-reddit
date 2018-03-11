package ch.zuehlke.reddit.data.source

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

import java.util.ArrayList
import java.util.LinkedHashMap

import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import ch.zuehlke.reddit.util.AndroidUtils

import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by chsc on 08.11.17.
 */

class RedditRepository
@Inject constructor(newsRemoteDataSource: RedditDataSource,
                    newsLocalDataSource: RedditDataSource,
                    private val androidUtils: AndroidUtils) : RedditDataSource {

    companion object {

        private val COMMENT_SECION = "comments/"

    }

    private val mRedditNewsRemoteDataSource: RedditDataSource

    private val mRedditNewsLocalDataSource: RedditDataSource

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    internal var mCacheNews: MutableMap<String, RedditNewsData>? = null

    /**
     * Marks the cache as invalid, to force an update the next time redditPost is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    internal var mCacheIsDirty = false


    init {
        mRedditNewsRemoteDataSource = checkNotNull(newsRemoteDataSource)
        mRedditNewsLocalDataSource = checkNotNull(newsLocalDataSource)
    }

    // FIXME: This setup seems not very sensible because we do not have a notion of the current location
    //        of news already in DB - I think we would need to store the current tag and pass that
    //        to the remote source
    override val news =
            if (isNetworkAvailable)
                Flowable.concatEager(
                        listOf(
                                newsLocalDataSource.news,
                                newsRemoteDataSource.news.map {
                                    it.forEach(newsLocalDataSource::saveRedditNews);
                                    it
                                }
                        ),1, 1
                )
            else
                newsLocalDataSource.news

    private val isNetworkAvailable: Boolean
        get() {
            return androidUtils.isNetworkAvailable()
        }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val convertedPermaLink = convertURLToRemote(permalink)
        mRedditNewsLocalDataSource.getPosts(object : RedditDataSource.LoadPostsCallback {
            override fun onPostsLoaded(posts: List<RedditPostsData>) {
                callback.onPostsLoaded(posts)
            }

            override fun onDataNotAvailable() {

            }
        }, convertedPermaLink)

        mRedditNewsRemoteDataSource.getPosts(object : RedditDataSource.LoadPostsCallback {
            override fun onPostsLoaded(posts: List<RedditPostsData>) {
                mRedditNewsLocalDataSource.deletePostsWithPermaLink(convertedPermaLink)
                for (data in posts) {
                    mRedditNewsLocalDataSource.savePosts(data)
                }
                callback.onPostsLoaded(posts)
            }

            override fun onDataNotAvailable() {

            }
        }, convertedPermaLink)

    }

    private fun convertURLToRemote(url: String): String {
        val parsedUrl = url.substring(url.indexOf(COMMENT_SECION) + COMMENT_SECION.length)
        return parsedUrl.substring(0, parsedUrl.length - 1)
    }

    override fun savePosts(data: RedditPostsData) {

    }

    override fun deletePostsWithPermaLink(permaLink: String) {

    }

    override fun deleteAllNews() {
        mRedditNewsRemoteDataSource.deleteAllNews() // Although we call deleteAllNews() on the remote datasource, it is not implemented.
        mRedditNewsLocalDataSource.deleteAllNews()

        if (mCacheNews == null) {
            mCacheNews = LinkedHashMap<String, RedditNewsData>()
        }
        mCacheNews!!.clear()
    }

    override fun saveRedditNews(data: RedditNewsData) {
        checkNotNull(data)

        mRedditNewsLocalDataSource.saveRedditNews(data)
        mRedditNewsRemoteDataSource.saveRedditNews(data) // Although we call saveRedditNews() on the remote datasource, it is not implemented.
        // Do in memory cache update to keep the app UI up to date
        if (mCacheNews == null) {
            mCacheNews = LinkedHashMap<String, RedditNewsData>()
        }
        mCacheNews!!.put(data.id!!, data)
    }

}
