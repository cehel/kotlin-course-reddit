package ch.zuehlke.reddit.data.source.local

import android.content.ContentValues
import android.content.Context

import java.util.ArrayList

import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData

import com.google.common.base.Preconditions.checkNotNull

/**
 * Created by chsc on 08.11.17.
 */

class RedditNewsLocalDataSource// Prevent direct instantiation.
private constructor(context: Context) : RedditDataSource {

    private val mDbHelper: RedditNewsDataHelper

    init {
        checkNotNull(context)
        mDbHelper = RedditNewsDataHelper(context)
    }

    override fun getMoreNews(callback: RedditDataSource.LoadNewsCallback) {
        throw UnsupportedOperationException("Not supported by local datasource")
    }

    override fun getNews(callback: RedditDataSource.LoadNewsCallback) {
        val redditNews = ArrayList<RedditNewsData>()
        val db = mDbHelper.readableDatabase

        val projection = arrayOf(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR, RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID)

        val c = db.query(
                RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, projection, null, null, null, null, null)

        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val newsId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID))
                val title = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE))
                val author = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR))
                val created = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED))
                val comments = c.getInt(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS))
                val thumbnail = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL))
                val permaLink = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK))
                val url = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL))

                val data = RedditNewsData(author, title, comments, created, thumbnail, url, newsId, permaLink)
                redditNews.add(data)
            }
        }
        c?.close()

        db.close()

        if (redditNews.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onNewsLoaded(redditNews)
        }
    }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val redditNews = ArrayList<RedditPostsData>()
        val db = mDbHelper.readableDatabase

        val projection = arrayOf(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR)

        val selection = RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK + " like '" + permalink + "'"

        val c = db.query(
                RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, projection, selection, null, null, null, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING)

        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val parentId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID))
                val postId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID))
                val depth = c.getInt(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH))
                val created = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED))
                val bodyHtml = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML))
                val body = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY))
                val permaLink = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK))
                val ordering = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING))
                val author = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR))

                val data = RedditPostsData(postId, parentId, author, body, created, depth, bodyHtml, permaLink, ordering)
                redditNews.add(data)
            }
        }
        c?.close()

        db.close()

        if (redditNews.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onPostsLoaded(redditNews)
        }
    }

    override fun savePosts(data: RedditPostsData) {

        checkNotNull(data)
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR, data.author)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY, data.body)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED, data.createdUtc)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID, data.id)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK, data.parentPermaLink)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH, data.depth)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID, data.parentId)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML, data.body_html)
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING, data.ordering)


        db.insert(RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, null, values)

        db.close()

    }

    override fun deletePostsWithPermaLink(permaLink: String) {

        val where = RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK + " like '" + permaLink + "'"

        val db = mDbHelper.writableDatabase
        db.delete(RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, where, null)
        db.close()
    }

    override fun refreshNews() {
        // Not required because the {@link RedditRepository} handles the logic of refreshing the
        // news from all the available redditPost sources.
    }

    override fun deleteAllNews() {
        val db = mDbHelper.writableDatabase
        db.delete(RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, null, null)
        db.close()
    }

    override fun saveRedditNews(data: RedditNewsData) {
        checkNotNull(data)
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR, data.author)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS, data.numberOfComments)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED, data.created)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID, data.id)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK, data.permaLink)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL, data.thumbnailUrl)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE, data.title)
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL, data.url)


        db.insert(RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, null, values)

        db.close()
    }

    companion object {


        private var INSTANCE: RedditNewsLocalDataSource? = null

        fun getInstance(context: Context): RedditNewsLocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = RedditNewsLocalDataSource(context)
            }
            return INSTANCE!!
        }
    }
}
