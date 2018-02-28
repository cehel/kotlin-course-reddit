package ch.zuehlke.sbb.reddit

import android.os.Bundle
import android.support.v4.app.Fragment
import ch.zuehlke.sbb.reddit.common.InstanceStateProvider

/**
 * Created by chsc on 28.02.18.
 */
open class BaseFragment: Fragment(){

    private companion object {
        private const val INSTANCE_STATE_PROVIDER_BUNDLE_NAME = "_savedInstancePersistence"
    }

    private val bundle = Bundle()

    protected fun <T> savedInstanceState() = InstanceStateProvider.Nullable<T>(bundle)
    protected fun <T> savedInstanceState(defaultValue: T) = InstanceStateProvider.NonNullable<T>(bundle,defaultValue)

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