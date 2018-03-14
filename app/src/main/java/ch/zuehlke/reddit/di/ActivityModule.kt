package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.di.scope.ActivityScope
import ch.zuehlke.reddit.features.login.LoginActivity
import ch.zuehlke.reddit.features.login.LoginActivityModule
import ch.zuehlke.reddit.features.news.NewsActivity
import ch.zuehlke.reddit.features.news.NewsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by celineheldner on 28.02.18.
 */
@Module
abstract class ActivityModule(){


    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    @ActivityScope
    abstract fun contributeLoginActivity() : LoginActivity

    @ContributesAndroidInjector(modules = arrayOf(NewsActivityModule::class))
    @ActivityScope
    abstract fun contributeNewsActivity() : NewsActivity

}