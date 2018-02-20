package ch.zuehlke.sbb.reddit.data

import android.content.Context
import android.support.annotation.NonNull
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.data.source.RedditDataSource
import ch.zuehlke.sbb.reddit.data.source.RemoteDataMapper
import ch.zuehlke.sbb.reddit.data.source.remote.model.news.RedditNewsAPIResponse
import ch.zuehlke.sbb.reddit.models.RedditNewsData
import ch.zuehlke.sbb.reddit.models.RedditPostsData
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by chsc on 08.11.17.
 */

class FakeRedditNewsRemoteDataSource// Prevent direct instantiation.
 constructor(contexttt: Context, dataMapper: RemoteDataMapper) : RedditDataSource {

    private val REDDIT_NEWS_SERVICE_DATA = LinkedHashMap<String, RedditNewsData>()
    private val mContext: Context
    private val mDataMapper: RemoteDataMapper
    private var order = 0;

    private var redditNewsDataList = ArrayList<RedditNewsData>()

    init {
        mContext = contexttt
        mDataMapper = dataMapper
    }

    companion object {

        private var INSTANCE: FakeRedditNewsRemoteDataSource? = null

        fun getInstance(context: Context, dataMapper: RemoteDataMapper): FakeRedditNewsRemoteDataSource{
            if (INSTANCE == null) {
                INSTANCE = FakeRedditNewsRemoteDataSource(context, dataMapper)
            }
            return INSTANCE!!
        }
    }

    override fun getMoreNews(callback: RedditDataSource.LoadNewsCallback) {
        callback.onNewsLoaded(redditNewsDataList)
    }

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val bufferedReader = mContext.resources.openRawResource(R.raw.reddit_posts).bufferedReader()
        order = 0
        var redditPosts: List<RedditPostsData>
        val elements = mDataMapper.parseResponseToPostElements(bufferedReader)
        redditPosts = mDataMapper.flattenRetrofitResponse(elements, permalink)
        callback.onPostsLoaded(redditPosts)
        val gson = Gson()
        val json = gson.fromJson(bufferedReader, RedditNewsAPIResponse::class.java)
    }


    override fun savePosts(data: RedditPostsData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getNews(@NonNull callback: RedditDataSource.LoadNewsCallback) {
        val bufferedReader = mContext.resources.openRawResource(R.raw.reddit_overview).bufferedReader()
        val gson = Gson()
        val json = gson.fromJson(bufferedReader, RedditNewsAPIResponse::class.java)
        for (child in json.data!!.children!!) {
            val data = child.data!!
            data.let {
                redditNewsDataList.add(RedditNewsData(data.author!!, data.title!!, data.num_comments, data.created, data.thumbnail!!, data.url!!, data.id!!, data.permalink!!))
            }
        }
        callback.onNewsLoaded(redditNewsDataList)
    }

    override fun refreshNews() {
        // Not required because the {@link ch.zuehlke.sbb.reddit.data.source.RedditRepository} handles the logic of refreshing the
        // news from all the available data sources.
    }

    override fun deleteAllNews() {
        // Not supported by Reddit :)
    }


    override fun saveRedditNews(@NonNull data: RedditNewsData) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }


}
