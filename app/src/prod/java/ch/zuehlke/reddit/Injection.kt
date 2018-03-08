package ch.zuehlke.reddit


import android.content.Context
import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.local.RedditNewsLocalDataSource
import ch.zuehlke.reddit.data.source.remote.RedditAPI
import ch.zuehlke.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import ch.zuehlke.reddit.data.source.remote.RedditNewsDataRemoteDataSource
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import com.google.common.base.Preconditions.checkNotNull
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier


/**
 * Enables injection of production implementations for
 * [ch.zuehlke.reddit.data.source.RedditDataSource] at compile time.
 */
object Injection {


    val type = object : TypeToken<List<RedditPostElement>>() {

    }.type


    val gson = GsonBuilder()
            .registerTypeAdapterFactory(elementTypeAdapterFactory)
            .create()
}
