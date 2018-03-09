package ch.zuehlke.reddit.data.source

import android.util.Log
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import ch.zuehlke.reddit.models.RedditPostsData
import com.google.common.base.Strings
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by celineheldner on 20.02.18.
 */
class RemoteDataMapper
private constructor(gson: Gson, type: Type){
    private val mGson: Gson
    private val mType: Type
    private val TAG = javaClass.canonicalName
    private var order = 0;

    init {
        mGson = gson
        mType = type
    }

    companion object {
        private var INSTANCE: RemoteDataMapper? = null

        fun getInstance(gson: Gson, type: Type): RemoteDataMapper {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataMapper(gson, type)
            }
            return INSTANCE!!
        }
    }

    fun flattenRetrofitResponse(response: List<RedditPostElement>, parentPermaLink: String): List<RedditPostsData> =
            response
                    .filter { it is RedditPostElement.DataRedditPostElement }
                    .map { it as RedditPostElement.DataRedditPostElement }
                    .flatMap { element ->
                        element.data?.let { data ->
                            if (element.kind == "Listing") {
                                recursivlyParseResponse(element, data.id, parentPermaLink)
                            } else {
                                listOf(RedditPostsData(data.id!!, null, data.author, data.body, data.created_utc, data.depth, data.body_html, data.permalink, 0))
                            }
                        }.orEmpty()
                    }
                    .foldIndexed(emptyList(), {i, acc, data -> acc + data.copy(ordering = i.toLong()) })

    private fun recursivlyParseResponse(dataRedditPostElement: RedditPostElement.DataRedditPostElement, parentId: String?, parentPermaLink: String): List<RedditPostsData> {
        val posts = dataRedditPostElement.data?.children.orEmpty()
        return posts
                .filter { it is RedditPostElement.DataRedditPostElement }
                .map { it as RedditPostElement.DataRedditPostElement }
                .flatMap { child ->
                    child.data?.let {
                        listOf(RedditPostsData(it.id!!, parentId, it.author, it.body, it.created_utc, it.depth, it.body_html, parentPermaLink, 0))
                    }.orEmpty()
                }
    }


    fun parseResponseToPostElements(response: ResponseBody): List<RedditPostElement> {
        var redditPostElements: List<RedditPostElement>? = null
        try {
            redditPostElements = mGson.fromJson<List<RedditPostElement>>(response.string(), mType)
        } catch (e: IOException) {
            Log.e(TAG,"Error while parsing respone $e")
        }

        return redditPostElements!!
    }

    fun parseResponseToPostElements(response: BufferedReader): List<RedditPostElement> {
        var redditPostElements: List<RedditPostElement>? = null
        try {
            redditPostElements = mGson.fromJson<List<RedditPostElement>>(response, mType)
        } catch (e: IOException) {
            Log.e("FakeRemote","Error while parsing respone $e")
        }

        return redditPostElements!!

    }
}