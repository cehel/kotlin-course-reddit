package ch.zuehlke.reddit

import android.app.Activity
import android.app.Application
import ch.zuehlke.reddit.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by celineheldner on 12.01.18.
 */

class RedditApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}
