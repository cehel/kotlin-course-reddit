package ch.zuehlke.sbb.reddit.data.source.local

import android.provider.BaseColumns

/**
 * Created by chsc on 08.11.17.
 */

object RedditNewsPersistenceContract {

    abstract class RedditNewsEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "redditnews"
            val COLUMN_NAME_AUTHOR = "author"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_COMMENTS = "comments"
            val COLUMN_NAME_CREATED = "createdAt"
            val COLUMN_NAME_THUMBNAIL = "thumbnail"
            val COLUMN_NAME_URL = "url"
            val COLUMN_NAME_ID = "entryId"
            val COLUMN_NAME_PERMA_LINK = "permaLink"
        }

    }

    object RedditPostEntry {
        val TABLE_NAME = "redditposts"
        val COLUMN_NAME_ID = "postId"
        val COLUMN_NAME_AUTHOR = "author"
        val COLUMN_NAME_PARENT_ID = "parentId"
        val COLUMN_NAME_BODY = "body"
        val COLUMN_NAME_CREATED = "createdAt"
        val COLUMN_NAME_DEPTH = "depth"
        val COLUMN_NAME_BODY_HTML = "bodyHTML"
        val COLUMN_NAME_PARENTPERMALINK = "parentPermaLink"
        val COLUMN_NAME_ORDERING = "ordering"
    }
}
