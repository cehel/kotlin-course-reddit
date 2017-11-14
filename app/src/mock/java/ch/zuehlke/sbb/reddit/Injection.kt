package ch.zuehlke.sbb.reddit

import android.content.Context

import ch.zuehlke.sbb.reddit.data.FakeRedditNewsRemoteDataSource
import ch.zuehlke.sbb.reddit.data.source.RedditNewsRepository
import ch.zuehlke.sbb.reddit.data.source.local.RedditNewsLocalDataSource

import com.google.common.base.Preconditions.checkNotNull

/**
 * Created by chsc on 08.11.17.
 */

object Injection {


    fun provideRedditNewsRepository(@NonNull context: Context): RedditNewsRepository {
        checkNotNull(context)
        return RedditNewsRepository.getInstance(FakeRedditNewsRemoteDataSource.getInstance(),
                RedditNewsLocalDataSource.getInstance(context))
    }
}
