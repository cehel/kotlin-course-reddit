package ch.zuehlke.sbb.reddit.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditNewsPersistenceContract {


    private RedditNewsPersistenceContract(){}

    public static abstract class RedditNewsEntry implements BaseColumns{
        public static final String TABLE_NAME = "redditnews";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_CREATED = "createdAt";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_ID = "entryId";
        public static final String COLUMN_NAME_PERMA_LINK= "permaLink";

    }

    public static abstract class RedditPostEntry {
        public static final String TABLE_NAME = "redditposts";
        public static final String COLUMN_NAME_ID = "postId";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PARENT_ID = "parentId";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_CREATED = "createdAt";
        public static final String COLUMN_NAME_DEPTH = "depth";
        public static final String COLUMN_NAME_BODY_HTML = "bodyHTML";
        public static final String COLUMN_NAME_PERMALINK = "permaLink";
        public static final String COLUMN_NAME_ORDERING = "ordering";
    }
}
