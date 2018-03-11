package ch.zuehlke.reddit.data.source.local

import android.content.ContentValues
import android.content.Context
import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.models.AppDatabase
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.ImmutableList
import javax.inject.Inject
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.util.*

/**
 * Created by chsc on 08.11.17.
 */

class RedditNewsLocalDataSource2
@Inject constructor(context: Context, db: AppDatabase) : RedditDataSource {

    private val mDbHelper: RedditNewsDataHelper
    private val mDb: AppDatabase

    init {
        checkNotNull(context)
        mDbHelper = RedditNewsDataHelper(context)
        mDb = db
    }

    override val news = getFlowableNews()

    fun getFlowableNews() = mDb.redditNewsDataDao().getFlowableNews()

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val posts = mDb.reditPostsDataDao().getPosts(permalink)
        if(posts.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onPostsLoaded(posts)
        }
    }

    override fun savePosts(data: RedditPostsData) {
        checkNotNull(data)
        mDb.reditPostsDataDao().addPostItems(ImmutableList.of(data))
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        mDb.reditPostsDataDao().deletePosts(permaLink)

    }


    override fun deleteAllNews() {
        mDb.redditNewsDataDao().deleteNews()
    }

    override fun saveRedditNews(data: RedditNewsData) {
        checkNotNull(data)
        mDb.redditNewsDataDao().addNewsItem(ImmutableList.of(data))
    }

}
