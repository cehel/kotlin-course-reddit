package ch.zuehlke.reddit.features

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ch.zuehlke.reddit.common.SimpleAppCompatNavigation
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by celineheldner on 07.03.18.
 */
open class BaseActivtiy : AppCompatActivity(), HasSupportFragmentInjector, SimpleAppCompatNavigation {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun supportFragmentInjector() = dispatchingAndroidInjector
}