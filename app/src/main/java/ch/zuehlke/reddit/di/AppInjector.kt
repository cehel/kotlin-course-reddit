package ch.zuehlke.reddit.di

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ch.zuehlke.reddit.RedditApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by celineheldner on 12.01.18.
 */


class AppInjector{

    companion object {
        fun init(app: RedditApp) {

            DaggerAppComponent.builder().application(app).build().inject(app)


            app.registerActivityLifecycleCallbacks(object: ActivityLifeCycleCallback(){
                 override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                    handleActivity(activity)
                }
            })
        }

        private fun handleActivity(activity: Activity) {
            if (activity is HasSupportFragmentInjector) {
                AndroidInjection.inject(activity)
            }
            if (activity is FragmentActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {

                    override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                        if(f is Injectable){
                            AndroidSupportInjection.inject(f)
                        }
                        super.onFragmentAttached(fm, f, context)
                    }
                }, true)
            }
        }
    }
}