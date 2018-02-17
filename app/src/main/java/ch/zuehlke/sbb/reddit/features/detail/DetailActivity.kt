package ch.zuehlke.sbb.reddit.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import ch.zuehlke.sbb.reddit.Injection
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.util.ActivityUtils

/**
 * Created by chsc on 11.11.17.
 */

class DetailActivity : AppCompatActivity() {

    private var mOverviewPresenter: DetailPresenter? = null
    private val TAG = "DetailActivity"

    companion object {
        val EXTRA_REDDIT_NEWS_URL_KEY = "redditNewsUrl"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Set up the toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        // Retrieve the data-url from the bundle
        val redditUrl = intent.getStringExtra(EXTRA_REDDIT_NEWS_URL_KEY)
        Log.i(TAG, "RedditUrl: " + redditUrl)

        // Create the fragment
        var detailFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as DetailFragment?
        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance()
            ActivityUtils.addFragmentToActivity(
                    supportFragmentManager, detailFragment, R.id.contentFrame)
        }

        // Create the presenter
        mOverviewPresenter = DetailPresenter(detailFragment, Injection.provideRedditNewsRepository(this), redditUrl)
    }


}
