package ch.zuehlke.reddit.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsDataDao
import ch.zuehlke.sbb.reddit.data.source.local.RedditPostsDataDao


/**
 * Created by Botz on 05.07.17.
 */
@Database(entities = arrayOf(RedditNewsData::class, RedditPostsData::class), version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun redditNewsDataDao() : RedditNewsDataDao
    abstract fun reditPostsDataDao() : RedditPostsDataDao
}
