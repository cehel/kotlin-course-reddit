package ch.zuehlke.reddit.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by chsc on 04.03.18.
 */
interface SimpleAppCompatNavigation {

    fun AppCompatActivity.addFragment(fragment: Fragment, containerId: Int) {
        checkNotNull(fragment)
        checkNotNull(containerId)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.apply {
            add(containerId, fragment)
                    .commit()
        }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, containerId: Int) {
        checkNotNull(fragment)
        checkNotNull(containerId)
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, fragment)
                    .addToBackStack(null).commit()
        }
    }

}