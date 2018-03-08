package ch.zuehlke.reddit.di

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by celineheldner on 12.09.17.
 */


open class ActivityLifeCycleCallback : Application.ActivityLifecycleCallbacks {
     override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
    }

}