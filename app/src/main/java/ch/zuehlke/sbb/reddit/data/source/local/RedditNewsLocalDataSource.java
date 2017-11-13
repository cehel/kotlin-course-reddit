package ch.zuehlke.sbb.reddit.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ch.zuehlke.sbb.reddit.data.source.RedditDataSource;
import ch.zuehlke.sbb.reddit.models.RedditNewsData;
import ch.zuehlke.sbb.reddit.models.RedditPostsData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditNewsLocalDataSource implements RedditDataSource {


    private static RedditNewsLocalDataSource INSTANCE;

    private RedditNewsDataHelper mDbHelper;

    // Prevent direct instantiation.
    private RedditNewsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new RedditNewsDataHelper(context);
    }

    public static RedditNewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RedditNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getMoreNews(@NonNull LoadNewsCallback callback) {
        throw new UnsupportedOperationException("Not supported by local datasource");
    }

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {
        List<RedditNewsData> redditNews = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR,
                RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID
        };

        Cursor c = db.query(
                RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String newsId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID));
                String title = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE));
                String author = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR));
                long created = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED));
                int comments = c.getInt(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS));
                String thumbnail = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL));
                String permaLink = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK));
                String url = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL));

                RedditNewsData data = new RedditNewsData(author,title,comments,created,thumbnail,url,newsId,permaLink);
                redditNews.add(data);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (redditNews.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onNewsLoaded(redditNews);
        }
    }

    @Override
    public void getPosts(@NonNull LoadPostsCallback callback, String permalink) {
        List<RedditPostsData> redditNews = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING,
                RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR
        };

        String selection =RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK +" like '" + permalink + "'";

        Cursor c = db.query(
                RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, projection, selection, null, null, null, RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String parentId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID));
                String postId = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID));
                int depth = c.getInt(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH));
                long created = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED));
                String bodyHtml = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML));
                String body = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY));
                String permaLink = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK));
                long ordering = c.getLong(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING));
                String author = c.getString(c.getColumnIndexOrThrow(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR));

                RedditPostsData data = new RedditPostsData(postId,parentId,author,body,created,depth,bodyHtml,permaLink,ordering);
                redditNews.add(data);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (redditNews.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onPostsLoaded(redditNews);
        }
    }

    @Override
    public void savePosts(@NonNull RedditPostsData data) {

        checkNotNull(data);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_AUTHOR,data.getAuthor());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY,data.getBody());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_CREATED,data.getCreatedUtc());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ID,data.getId());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK,data.getParentPermaLink());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_DEPTH,data.getDepth());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENT_ID,data.getParentId());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_BODY_HTML,data.getBody_html());
        values.put(RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_ORDERING,data.getOrdering());


        db.insert(RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, null, values);

        db.close();

    }

    @Override
    public void deletePostsWithPermaLink(@NonNull String permaLink){

        String where = RedditNewsPersistenceContract.RedditPostEntry.COLUMN_NAME_PARENTPERMALINK +" like '"+permaLink+"'";

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(RedditNewsPersistenceContract.RedditPostEntry.TABLE_NAME, where, null);
        db.close();
    }

    @Override
    public void refreshNews() {
        // Not required because the {@link RedditRepository} handles the logic of refreshing the
        // news from all the available redditPost sources.
    }

    @Override
    public void deleteAllNews() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void saveRedditNews(@NonNull RedditNewsData data) {
        checkNotNull(data);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR,data.getAuthor());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS,data.getNumberOfComments());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED,data.getCreated());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID,data.getId());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK,data.getPermaLink());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL,data.getThumbnailUrl());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE,data.getTitle());
        values.put(RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL,data.getUrl());


        db.insert(RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME, null, values);

        db.close();
    }
}
