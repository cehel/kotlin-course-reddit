package ch.zuehlke.reddit

import ch.zuehlke.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by chsc on 08.11.17.
 */

object Injection {

    val gson = GsonBuilder()
            .registerTypeAdapterFactory(elementTypeAdapterFactory)
            .create()


    val type = object : TypeToken<List<RedditPostElement>>() {

    }.type

}
