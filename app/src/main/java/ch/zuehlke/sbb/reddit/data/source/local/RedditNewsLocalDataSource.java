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
    public void getPosts(@NonNull LoadPostsCallback callback, String title) {

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
