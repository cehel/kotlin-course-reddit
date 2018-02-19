package ch.zuehlke.sbb.reddit.features.news

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import ch.zuehlke.sbb.reddit.Injection
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseActivity
import ch.zuehlke.sbb.reddit.features.news.detail.DetailFragment
import ch.zuehlke.sbb.reddit.features.news.detail.DetailPresenter
import ch.zuehlke.sbb.reddit.features.news.overview.OverviewFragment
import ch.zuehlke.sbb.reddit.features.news.overview.OverviewPresenter
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
        OverviewPresenter(overviewFragment, Injection.provideRedditNewsRepository(mActivity))

    }

    fun showDetails(redditUrl: String?){
        // Create the fragment
        val detailFragment: DetailFragment = DetailFragment.newInstance(redditUrl!!)
        ActivityUtils.replaceFragmentToActivity(
                mActivity.supportFragmentManager, detailFragment, mContainerId!!)
        DetailPresenter(detailFragment,Injection.provideRedditNewsRepository(mActivity),redditUrl)
    }
}