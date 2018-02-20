package ch.zuehlke.sbb.reddit.data.source.remote

import android.content.Context
import android.util.Log
import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.data.source.RemoteDataMapper
import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIResponse
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import com.google.common.base.Preconditions.checkNotNull
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*


/**
 * Created by chsc on 08.11.17.
 */

class RedditNewsDataRemoteDataSource// Prevent direct instantiation.
private constructor(context: Context, redditAPI: RedditAPI, dataMapper: RemoteDataMapper) : RedditDataSource {
    private var after = ""
    private var order = -1
    private val mRedditAPI: RedditAPI
    private val mDataMapper: RemoteDataMapper
    private val TAG = "RemoteDataSource"


    init {
        checkNotNull(context)
        mRedditAPI = checkNotNull(redditAPI, "The reddit api cannot be null")
        mDataMapper = dataMapper

    }

    companion object {
        private var INSTANCE: RedditNewsDataRemoteDataSource? = null

        fun getInstance(context: Context, redditAPI: RedditAPI, dataMapper: RemoteDataMapper): RedditNewsDataRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RedditNewsDataRemoteDataSource(context, redditAPI, dataMapper)
            }
            return INSTANCE!!
        }
    }


    override fun getMoreNews(callback: RedditDataSource.LoadNewsCallback) {
        val call = mRedditAPI.getSortedNews("hot", after, "10")
        call.enqueue(object : Callback<RedditNewsAPIResponse> {
            override fun onResponse(call: Call<RedditNewsAPIResponse>, response: Response<RedditNewsAPIResponse>) {
                after = response.body().data!!.after!!
                Log.i(TAG, "Recieved reddit response: " + response.body())
                Log.i(TAG,Gson().toJson(response.body()))
                val redditNewsDataList = ArrayList<RedditNewsData>()
                for (child in response.body().data!!.children!!) {
                    val data = child.data
                    Log.i(TAG, "child date: " + Date(data!!.created))
                    data.let {
                        redditNewsDataList.add(RedditNewsData(data.author!!, data.title!!, data.num_comments, data.created, data.thumbnail!!, data.url!!, data.id!!, data.permalink!!))

                    }
                     }
                callback.onNewsLoaded(redditNewsDataList)
            }

            override fun onFailure(call: Call<RedditNewsAPIResponse>, t: Throwable) {
                Log.e(TAG, "Error while requesting reddit news: ", t)
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getNews(callback: RedditDataSource.LoadNewsCallback) {


        val call = mRedditAPI.getSortedNews("hot", "", "10")
        call.enqueue(object : Callback<RedditNewsAPIResponse> {
            override fun onResponse(call: Call<RedditNewsAPIResponse>, response: Response<RedditNewsAPIResponse>) {
                after = response.body().data!!.after!!
                Log.i(TAG, "Recieved reddit response: " + response.body())
                val redditNewsDataList = ArrayList<RedditNewsData>()
                for (child in response.body().data!!.children!!) {
                    val data = child.data
                    data?.let {
                        redditNewsDataList.add(RedditNewsData(data.author!!, data.title!!, data.num_comments, data.created, data.thumbnail!!, data.url!!, data.id!!, data.permalink!!))
                    }

                }
                callback.onNewsLoaded(redditNewsDataList)
            }

            override fun onFailure(call: Call<RedditNewsAPIResponse>, t: Throwable) {
                Log.e(TAG, "Error while requesting reddit news: ", t)
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val call = mRedditAPI.getRedditPosts(permalink, "new")
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i(TAG,"Got Posts from Remote!!")
                Log.i(TAG,"posts:"+response.body().string())
                var redditPosts: List<RedditPostsData>
                val elements = mDataMapper.parseResponseToPostElements(response.body())

                order = 0
                redditPosts = mDataMapper.flattenRetrofitResponse(elements, permalink)
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


    override fun refreshNews() {
        after = ""
    }

    override fun deleteAllNews() {
        // Not supported by Reddit :)
    }

    override fun saveRedditNews(data: RedditNewsData) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }


}
