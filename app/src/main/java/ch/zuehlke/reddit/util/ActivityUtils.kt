package ch.zuehlke.reddit.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import com.google.common.base.Preconditions.checkNotNull

/**
 * Created by chsc on 08.11.17.
 *
 * chapter_02_section_05_extension_exercise
 *
 * TODO(This class should be removed and the logic should be placed inside a (dispatching)extension for the correct receiver )
 */

object ActivityUtils {

    /**
     * The `fragment` is added to the container view with id `frameId`. The operation is
     * performed by the `fragmentManager`.

     */
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

    fun replaceFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment).addToBackStack( null )
        transaction.commit()
    }


}
