package ch.zuehlke.reddit.features.news

import android.os.Bundle
import android.support.v7.widget.Toolbar
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.features.BaseActivtiy
import javax.inject.Inject

/**
 * Created by chsc on 11.11.17.
 */

class NewsActivity : BaseActivtiy() {

    @Inject
    lateinit var mNavigationController : NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_news)

        // Set up the toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(false)


        mNavigationController?.showOverview()
    }


}
