package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.data.source.RedditRepository
import ch.zuehlke.reddit.features.login.FakeSharedPreferences
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
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
    fun provideRedditNewsRepository() = Mockito.mock(RedditRepository::class.java)
}