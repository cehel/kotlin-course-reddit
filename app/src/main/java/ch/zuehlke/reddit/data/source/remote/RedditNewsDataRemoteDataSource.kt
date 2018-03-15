package ch.zuehlke.reddit.data.source.remote

import android.content.Context
import android.util.Log
import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.remote.model.news.RedditNewsAPIResponse
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import com.google.common.base.Preconditions.checkNotNull
import com.google.gson.Gson
import io.reactivex.Emitter
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


/**
 * Created by chsc on 08.11.17.
 */

class RedditNewsDataRemoteDataSource
@Inject constructor(context: Context, redditAPI: RedditAPI, dataMapper: RemoteDataMapper) : RedditDataSource {
    private val mRedditAPI: RedditAPI
    private val mDataMapper: RemoteDataMapper
    private val TAG = "RemoteDataSource"


    init {
        checkNotNull(context)
        mRedditAPI = checkNotNull(redditAPI, "The reddit api cannot be null")
        mDataMapper = dataMapper

    }


    private val initialTag = {
        Log.i(TAG,"Provide initial tag")
        ""
    }
    private val nextValue = { currentTag: String, emitter: Emitter<List<RedditNewsData>> ->
        try {
            Log.i(TAG, "Loading next 10 hot news from tag $currentTag")
            val response = redditAPI.getSortedNews("hot", currentTag, "10").execute().body()
            val newsList = response?.data?.children?.map { child ->
                child.data?.let { data ->
                    RedditNewsData(data.author!!, data.title!!, data.num_comments, data.created, data.thumbnail!!, data.url!!, data.id!!, data.permalink!!)
                }
            } ?: emptyList()
            val nextTag = response?.data?.after ?: ""
            Log.i(TAG,"Got more news: $newsList")
            emitter.onNext(newsList.filterNotNull())
            nextTag
        } catch (error: Exception) {
            emitter.onError(error);
            ""
        }
    }
    override val news: Flowable<List<RedditNewsData>> = Flowable.generate(initialTag, nextValue)

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val call = mRedditAPI.getRedditPosts(permalink, "new")
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i(TAG,"Got Posts from Remote!!")
                val elements = mDataMapper.parseResponseToPostElements(response.body())
                val redditPosts = mDataMapper.flattenRetrofitResponse(elements, permalink)
                callback.onPostsLoaded(redditPosts)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onDataNotAvailable()
            }
        })

    }

    override fun savePosts(data: RedditPostsData) {
        //Remotly its not used
    }

    override fun deletePostsWithPermaLink(permaLink: String) {

    }

    override fun deleteAllNews() {
        // Not supported by Reddit :)
    }

    override fun saveRedditNews(data: RedditNewsData) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }


}
