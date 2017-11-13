package ch.zuehlke.sbb.reddit.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chsc on 08.11.17.
 */

public class RedditNewsDataHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "RedditNews.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String LONG_TYPE =  " INTEGER ";

    private static final String COMMA_SEP = ",";


    private static final String SQL_CREATE_ENTRIES =  "CREATE TABLE " + RedditNewsPersistenceContract.RedditNewsEntry.TABLE_NAME + " (" +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_ID + TEXT_TYPE + " PRIMARY_KEY" + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_COMMENTS + LONG_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_CREATED + LONG_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_PERMA_LINK + TEXT_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            RedditNewsPersistenceContract.RedditNewsEntry.COLUMN_NAME_URL + TEXT_TYPE +
            " )";




    public RedditNewsDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Not needed for version 1
    }
}
