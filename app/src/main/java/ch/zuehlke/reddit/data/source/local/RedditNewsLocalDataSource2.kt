package ch.zuehlke.reddit.data.source.local

import android.content.Context
import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.models.AppDatabase
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.ImmutableList
import io.reactivex.Completable
import io.reactivex.Scheduler
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by chsc on 08.11.17.
 */

class RedditNewsLocalDataSource2
@Inject constructor(context: Context, db: AppDatabase,
                    @Named("io-scheduler") private val ioScheduler: Scheduler) : RedditDataSource {

    private val mDb: AppDatabase

    init {
        checkNotNull(context)
        mDb = db
    }

    override val news = mDb.redditNewsDataDao().getSingleNews().toFlowable()

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        async {
            val posts = mDb.reditPostsDataDao().getPosts(permalink)
            if(posts.isEmpty()) {
                // This will be called if the table is new or just empty.
                callback.onDataNotAvailable()
            } else {
                callback.onPostsLoaded(posts)
            }
        }
    }

    override fun savePosts(data: RedditPostsData) {
        checkNotNull(data)
        Completable.create{
            mDb.reditPostsDataDao().addPostItems(ImmutableList.of(data))
        }.subscribeOn(ioScheduler).subscribe()
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        Completable.create{
            mDb.reditPostsDataDao().deletePosts(permaLink)
        }.subscribeOn(ioScheduler).subscribe()

    }


    override fun deleteAllNews() {
        Completable.create{
            mDb.redditNewsDataDao().deleteNews()
        }.subscribeOn(ioScheduler).subscribe()
    }

    override fun saveRedditNews(data: RedditNewsData) {
        checkNotNull(data)
        Completable.create{
            mDb.redditNewsDataDao().addNewsItem(ImmutableList.of(data))
        }.subscribeOn(ioScheduler).subscribe()
    }

}
