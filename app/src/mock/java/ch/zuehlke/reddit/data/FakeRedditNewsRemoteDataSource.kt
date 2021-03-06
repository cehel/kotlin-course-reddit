package ch.zuehlke.reddit.data

import android.content.Context
import android.support.annotation.NonNull
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.data.source.RedditDataSource
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.remote.model.news.RedditNewsAPIResponse
import ch.zuehlke.reddit.models.RedditNewsData
import ch.zuehlke.reddit.models.RedditPostsData
import com.google.gson.Gson
import io.reactivex.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by chsc on 08.11.17.
 */

class FakeRedditNewsRemoteDataSource
@Inject constructor(context: Context, dataMapper: RemoteDataMapper) : RedditDataSource {

    private val mContext: Context
    private val mDataMapper: RemoteDataMapper
    private var order = 0;

    private var redditNewsDataList = ArrayList<RedditNewsData>()

    init {
        mContext = context
        mDataMapper = dataMapper
    }

    override val news = Single.create(SingleOnSubscribe { emitter: SingleEmitter<List<RedditNewsData>> ->
        val bufferedReader = mContext.resources.openRawResource(R.raw.reddit_overview).bufferedReader()
        val gson = Gson()
        val json = gson.fromJson(bufferedReader, RedditNewsAPIResponse::class.java)
        for (child in json.data!!.children!!) {
            val data = child.data!!
            data.let {
                redditNewsDataList.add(RedditNewsData(data.author!!, data.title!!, data.num_comments, data.created, data.thumbnail!!, data.url!!, data.id!!, data.permalink!!))
            }
        }
        emitter.onSuccess(redditNewsDataList.toList())
    }).toFlowable()

    override fun getPosts(callback: RedditDataSource.LoadPostsCallback, permalink: String) {
        val bufferedReader = mContext.resources.openRawResource(R.raw.reddit_posts).bufferedReader()
        order = 0

        val elements = mDataMapper.parseResponseToPostElements(bufferedReader)
        val redditPosts = mDataMapper.flattenRetrofitResponse(elements, permalink)
        callback.onPostsLoaded(redditPosts)
    }


    override fun savePosts(data: RedditPostsData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePostsWithPermaLink(permaLink: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun deleteAllNews() {
        // Not supported by Reddit :)
    }


    override fun saveRedditNews(@NonNull data: RedditNewsData) {
        // In this demo app we do not support posting of news, therefore not implemented.
    }


}
