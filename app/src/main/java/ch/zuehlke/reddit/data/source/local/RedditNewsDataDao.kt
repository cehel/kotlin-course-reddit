package ch.zuehlke.sbb.reddit.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ch.zuehlke.reddit.models.RedditNewsData
import io.reactivex.Flowable

/**
 * Created by celineheldner on 16.11.17.
 */
@Dao
interface RedditNewsDataDao{


    @Query("select * from RedditNewsData")
    fun getNews(): List<RedditNewsData>

    @Query("select * from RedditNewsData")
    fun getFlowableNews(): Flowable<List<RedditNewsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsItem(item: List<RedditNewsData>)

    @Query("delete from RedditNewsData")
    fun deleteNews()


}