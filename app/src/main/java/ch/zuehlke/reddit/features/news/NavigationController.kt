package ch.zuehlke.reddit.features.news

import android.support.v4.app.Fragment
import ch.zuehlke.reddit.features.BaseActivtiy
import ch.zuehlke.reddit.features.news.overview.OverviewFragment


/**
 * Created by celineheldner on 17.11.17.
 */
class NavigationController constructor(private val activity: BaseActivtiy, private val fragmentContainerId: Int)  {

    fun showOverview() {
        var overviewFragment: OverviewFragment? = activity.supportFragmentManager.findFragmentById(fragmentContainerId!!) as OverviewFragment?
        if (overviewFragment == null) {
            // Create the fragment
            overviewFragment = OverviewFragment.newInstance()
            activity.apply {
                addFragment(overviewFragment!!, fragmentContainerId)
            }
        }
    }

    fun navigateToFragment(fragment: Class<out Fragment>) {
        activity.apply {
            replaceFragment(fragment.newInstance(), fragmentContainerId)
        }
    }
}