package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.features.login.FakeSharedPreferences
import ch.zuehlke.reddit.models.RedditNewsData
import com.google.common.collect.ImmutableList
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by celineheldner on 09.03.18.
 */
@Module
class AppModuleTest{




    @Provides
    @Singleton
    fun provideSharedPreferenceHolder() = FakeSharedPreferences()


    @Provides
    @Singleton
    @Named("io-scheduler")
    fun provideIoScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @Named("main-scheduler")
    fun provideMainScheduler() = AndroidSchedulers.mainThread()


    @Provides
    @Singleton
    fun provideRedditNewsRepository() = mock<RedditRepository>(){ on{news} doReturn
        Single.create({ emitter: SingleEmitter<List<RedditNewsData>> ->
            emitter.onSuccess(ImmutableList.of())
        }).toFlowable()
    }
}