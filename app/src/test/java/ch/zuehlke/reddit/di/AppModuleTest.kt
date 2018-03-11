package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.features.login.FakeSharedPreferences
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
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

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable,
                                    delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(
                    Executor { it.run() })
        }
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceHolder() = FakeSharedPreferences()




    @Provides
    @Singleton
    fun provideRedditNewsRepository() = Mockito.mock(RedditRepository::class.java)
}