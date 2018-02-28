package ch.zuehlke.sbb.reddit.features.news

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import ch.zuehlke.sbb.reddit.Injection
import ch.zuehlke.sbb.reddit.features.news.detail.DetailFragment
import ch.zuehlke.sbb.reddit.features.news.overview.OverviewFragment
import ch.zuehlke.sbb.reddit.util.ActivityUtils

/**
 * Created by celineheldner on 17.11.17.
 */
class NavigationController constructor(activity: FragmentActivity, fragmentContainerId: Int?){
    val mActivity = activity
    val mContainerId = fragmentContainerId

    fun showOverview(){
        var overviewFragment: OverviewFragment? = mActivity.supportFragmentManager.findFragmentById(mContainerId!!) as OverviewFragment?
        if (overviewFragment == null) {
            // Create the fragment
            overviewFragment = OverviewFragment.newInstance()
            ActivityUtils.addFragmentToActivity(
                    mActivity.supportFragmentManager, overviewFragment!!, mContainerId)
        }


    }

    fun navigateToFragment(fragment: Class<out Fragment>){
        ActivityUtils.replaceFragmentToActivity(
                mActivity.supportFragmentManager, fragment.newInstance(), mContainerId!!)
    }
}