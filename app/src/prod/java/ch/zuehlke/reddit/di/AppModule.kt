package ch.zuehlke.reddit.di

import android.app.Application
import android.content.Context
import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.local.RedditNewsLocalDataSource
import ch.zuehlke.reddit.data.source.remote.RedditAPI
import ch.zuehlke.reddit.data.source.remote.RedditElementTypeAdapterFactory
import ch.zuehlke.reddit.data.source.remote.RedditNewsDataRemoteDataSource
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import com.google.common.base.Preconditions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import javax.inject.Singleton

/**
 * Created by celineheldner on 28.02.18.
 */
@Module
class AppModule(){

    private val REDDIT_END_POINT = "https://www.reddit.com/r/kotlin/"

    @Provides
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
            .registerTypeAdapterFactory(RedditElementTypeAdapterFactory.elementTypeAdapterFactory)
            .create()

    @Provides
    @Singleton
    fun provideType() = object : TypeToken<List<RedditPostElement>>() {}.type

    @Provides
    @Singleton
    fun provideRemoteMapper(gson: Gson, type: Type) = RemoteDataMapper.getInstance(gson, type)

    @Provides
    @Singleton
    fun provideRetrofit(): RedditAPI {
        val gson = GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapterFactory(RedditElementTypeAdapterFactory.elementTypeAdapterFactory)
                .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        return Retrofit.Builder()
                .baseUrl(REDDIT_END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create<RedditAPI>(RedditAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRedditRemoteDataSource(context: Context, redditAPI: RedditAPI, dataMapper: RemoteDataMapper) = RedditNewsDataRemoteDataSource(context,redditAPI,dataMapper)

    @Provides
    @Singleton
    fun provideRedditLocalDataSource(context: Context) = RedditNewsLocalDataSource(context)


    @Provides
    @Singleton
    fun provideRedditNewsRepository(context: Context, redditNewsDataRemoteDataSource: RedditNewsDataRemoteDataSource, redditNewsLocalDataSource: RedditNewsLocalDataSource): RedditRepository {
        Preconditions.checkNotNull(context)
        return RedditRepository(redditNewsDataRemoteDataSource,
                redditNewsLocalDataSource, context)
    }
    
}