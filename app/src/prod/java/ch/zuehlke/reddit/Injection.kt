package ch.zuehlke.reddit


import android.content.Context
import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.local.RedditNewsLocalDataSource
import ch.zuehlke.reddit.data.source.remote.RedditAPI
import ch.zuehlke.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import ch.zuehlke.reddit.data.source.remote.RedditNewsDataRemoteDataSource
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import ch.zuehlke.reddit.features.login.PreferencesHolder
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

    private val PREFERENCES_NAME = "redditApp"

    private val REDDIT_END_POINT = "https://www.reddit.com/r/kotlin/"

    private var redditAPI: RedditAPI? = null
    private var retrofit: Retrofit? = null

    val type = object : TypeToken<List<RedditPostElement>>() {

    }.type


    val gson = GsonBuilder()
            .registerTypeAdapterFactory(elementTypeAdapterFactory)
            .create()

    val remoteDataMapper = RemoteDataMapper.getInstance(gson, type)

    fun provideRedditNewsRepository(context: Context): RedditRepository {
        checkNotNull(context)
        return RedditRepository.getInstance(RedditNewsDataRemoteDataSource.getInstance(context, getRedditAPI(retroFit), remoteDataMapper),
                RedditNewsLocalDataSource.getInstance(context), context)
    }

    fun provideSharedPreferencesHolder(context: Context): PreferencesHolder =
            PreferencesHolder(context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE))

    fun getRedditAPI(retrofit: Retrofit): RedditAPI {
        if (redditAPI == null) {
            redditAPI = retrofit.create<RedditAPI>(RedditAPI::class.java!!)
        }
        return redditAPI!!
    }

    val retroFit: Retrofit
        get() {
            if (retrofit == null) {
                val gson = GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapterFactory(elementTypeAdapterFactory)
                        .create()

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


                retrofit = Retrofit.Builder()
                        .baseUrl(REDDIT_END_POINT)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit!!
        }
}
