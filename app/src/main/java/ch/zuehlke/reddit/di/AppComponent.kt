package ch.zuehlke.reddit.di

import android.app.Application
import ch.zuehlke.reddit.RedditApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by celineheldner on 28.02.18.
 */
@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class))
interface AppComponent{

    fun inject(app: RedditApp)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(redditApp: Application): Builder
        fun build(): AppComponent
    }

}