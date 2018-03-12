package ch.zuehlke.reddit

import android.content.Context
import android.support.annotation.NonNull
import ch.zuehlke.reddit.data.FakeRedditNewsRemoteDataSource
import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.data.source.RemoteDataMapper
import ch.zuehlke.reddit.data.source.local.RedditNewsLocalDataSource
import ch.zuehlke.reddit.data.source.remote.RedditElementTypeAdapterFactory.Companion.elementTypeAdapterFactory
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import ch.zuehlke.reddit.features.login.PreferencesHolder
import com.google.common.base.Preconditions.checkNotNull
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by chsc on 08.11.17.
 */

object Injection {

    private val PREFERENCES_NAME = "redditApp"

    val gson = GsonBuilder()
            .registerTypeAdapterFactory(elementTypeAdapterFactory)
            .create()


    val type = object : TypeToken<List<RedditPostElement>>() {

    }.type

    val remoteDataMapper = RemoteDataMapper.getInstance(gson, type)

    fun provideRedditNewsRepository(@NonNull context: Context): RedditRepository {
        checkNotNull(context)
        return RedditRepository.getInstance(FakeRedditNewsRemoteDataSource.getInstance(context, remoteDataMapper),
                RedditNewsLocalDataSource.getInstance(context), context)
    }

    fun provideSharedPreferencesHolder(context: Context): PreferencesHolder =
            PreferencesHolder(context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE))


}
