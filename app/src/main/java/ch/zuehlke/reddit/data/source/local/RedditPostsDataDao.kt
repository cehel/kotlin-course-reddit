package ch.zuehlke.sbb.reddit.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ch.zuehlke.reddit.models.RedditPostsData
import io.reactivex.Flowable

/**
 * Created by celineheldner on 16.11.17.
 */
@Dao
interface RedditPostsDataDao{

    @Query("select * from redditPosts where parentPermaLink = :permalink order by ordering")
    fun getPosts(permalink: String?): List<RedditPostsData>

    @Query("select * from redditPosts where parentPermaLink = :permalink order by ordering")
    fun getPostsFlowable(permalink: String?): Flowable<List<RedditPostsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPostItems(item: List<RedditPostsData>)

    @Query("delete from redditPosts where parentPermaLink = :permalink")
    fun deletePosts(permalink: String?)

}