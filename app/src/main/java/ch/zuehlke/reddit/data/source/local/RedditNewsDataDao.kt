package ch.zuehlke.sbb.reddit.data.source.local

import android.arch.persistence.room.Dao
import ch.zuehlke.reddit.models.RedditNewsData
import io.reactivex.Single

/**
 * Created by celineheldner on 16.11.17.
 */
@Dao
interface RedditNewsDataDao{


    //TODO: room_exercise1: Füge die nötige Room Annotation hinzu
    fun getNews(): List<RedditNewsData>

    //TODO: room_exercise1: Füge die nötige Room Annotation hinzu
    fun getSingleNews(): Single<List<RedditNewsData>>

    //TODO: room_exercise1: Füge die nötige Room Annotation hinzu
    fun addNewsItem(item: List<RedditNewsData>)

    //TODO: room_exercise1: Füge die nötige Room Annotation hinzu
    fun deleteNews()


}