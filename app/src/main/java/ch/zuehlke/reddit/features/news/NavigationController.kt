package ch.zuehlke.reddit.features.news

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.zuehlke.reddit.common.SimpleAppCompatNavigation
import ch.zuehlke.reddit.features.news.overview.OverviewFragment


/**
 * Created by celineheldner on 17.11.17.
 */
class NavigationController<out T> constructor(private val activity: T, private val fragmentContainerId: Int) where T : AppCompatActivity, T : SimpleAppCompatNavigation {

    fun showOverview() {
        var overviewFragment: OverviewFragment? = activity.supportFragmentManager.findFragmentById(fragmentContainerId!!) as OverviewFragment?
        if (overviewFragment == null) {
            // Create the fragment
            overviewFragment = OverviewFragment.newInstance()
        }
        activity.apply {
            addFragment(overviewFragment!!, fragmentContainerId)
        }
    }

    fun navigateToFragment(fragment: Class<out Fragment>) {
        activity.apply {
            replaceFragment(fragment.newInstance(), fragmentContainerId)
        }
    }
}