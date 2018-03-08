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
import java.util.ArrayList

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

    fun flattenRetrofitResponse(response: List<RedditPostElement>, parentPermaLink: String): List<RedditPostsData> {
        val flatten = ArrayList<RedditPostsData>()
        for (redditPostElement in response) {
            if (redditPostElement is RedditPostElement.DataRedditPostElement) {
                val dataElement = redditPostElement
                val data = dataElement.data
                if (dataElement.data != null) {
                    if (!Strings.isNullOrEmpty(dataElement.data.body_html)) {
                        data?.let {
                            val postData = RedditPostsData(data.id!!, null, data.author!!, data.body!!, data.created_utc, data.depth, data.body_html!!, data.permalink!!, order++.toLong())
                            flatten.add(postData)
                        }

                    } else {
                        data?.let {
                            flatten.addAll(recursivlyParseResponse(dataElement, data.id, parentPermaLink))
                        }

                    }
                }
            }
        }
        return flatten
    }

    private fun recursivlyParseResponse(dataRedditPostElement: RedditPostElement.DataRedditPostElement, parentId: String?, parentPermaLink: String): List<RedditPostsData> {
        val posts = ArrayList<RedditPostsData>()
        for (child in dataRedditPostElement.data!!.children!!) {
            if (child is RedditPostElement.DataRedditPostElement) {
                val data = child.data
                if (data != null) {
                    val postData = RedditPostsData(data.id!!, parentId, data.author, data.body, data.created_utc, data.depth, data.body_html, parentPermaLink, order++.toLong())
                    posts.add(postData)
                    if (data.replies != null && data.replies is RedditPostElement.DataRedditPostElement) {
                        posts.addAll(recursivlyParseResponse(data.replies as RedditPostElement.DataRedditPostElement, data.id!!, parentPermaLink))
                    }
                }
            }

        }
        return posts
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