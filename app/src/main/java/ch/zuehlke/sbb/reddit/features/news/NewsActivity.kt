package ch.zuehlke.sbb.reddit.features.news

import android.os.Bundle
import android.support.v7.widget.Toolbar
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.BaseActivity
/**
 * Created by chsc on 11.11.17.
 */

class NewsActivity : BaseActivity() {

    private var mNavigationController: NavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_overview)

        mNavigationController = NavigationController(this, R.id.contentFrame)

        // Set up the toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(false)

        mNavigationController!!.showOverview()
    }


}
