package ch.zuehlke.reddit.features.news

import android.os.Bundle
import android.support.v7.widget.Toolbar
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.features.BaseActivtiy

/**
 * Created by chsc on 11.11.17.
 */

class NewsActivity : BaseActivtiy() {

    private var mNavigationController: NavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_news)

        mNavigationController = NavigationController(this, R.id.contentFrame)

        // Set up the toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(false)


        mNavigationController?.showOverview()
    }


}
