package ch.zuehlke.reddit

import android.os.Bundle
import android.support.v4.app.Fragment
import ch.zuehlke.reddit.common.InstanceStateProvider

/**
 * Created by chsc on 28.02.18.
 */
open class BaseFragment: Fragment(){

    private companion object {
        private const val INSTANCE_STATE_PROVIDER_BUNDLE_NAME = "_savedInstancePersistence"
    }

    private val bundle = Bundle()

    /*
     chapter_02_section_01_property_delegates_exercise
     TODO Instantiate the property delegate(s) with the bundle above so that all properties are persisted in the same bundle.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            bundle.putAll(savedInstanceState.getBundle(INSTANCE_STATE_PROVIDER_BUNDLE_NAME))
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBundle(INSTANCE_STATE_PROVIDER_BUNDLE_NAME,bundle)
        super.onSaveInstanceState(outState)
    }
}