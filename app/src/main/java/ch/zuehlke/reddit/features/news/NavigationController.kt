package ch.zuehlke.reddit.features.news

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import ch.zuehlke.reddit.features.news.overview.OverviewFragment
import ch.zuehlke.reddit.util.ActivityUtils

/**
 * Created by celineheldner on 17.11.17.
 *
 * chapter_02_section_05_extension_exercise
 *
 * TODO(This class needs to be refactored so that it can work with the dispatching interface.)
 * Introduce a generic constraint T which constrict the possible user of this class
 */
class NavigationController constructor(activity: FragmentActivity, fragmentContainerId: Int?){
    val mActivity = activity
    val mContainerId = fragmentContainerId

    fun showOverview(){
        var overviewFragment: OverviewFragment? = mActivity.supportFragmentManager.findFragmentById(mContainerId!!) as OverviewFragment?
        if (overviewFragment == null) {
            // Create the fragment
            overviewFragment = OverviewFragment.newInstance()
            /*
            chapter_02_section_05_extension_exercise

            To use the functionality of a dispatching interface which is applied on a given class,
            u need to work in the context of this class to get access to the methods defined in the
            dispatching interface -> apply{}
             */
            ActivityUtils.addFragmentToActivity(
                    mActivity.supportFragmentManager, overviewFragment!!, mContainerId)
        }


    }

    fun navigateToFragment(fragment: Class<out Fragment>){
        ActivityUtils.replaceFragmentToActivity(
                mActivity.supportFragmentManager, fragment.newInstance(), mContainerId!!)
    }
}