package ch.zuehlke.reddit.features.login

import android.os.Bundle

import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.features.BaseActivtiy
import ch.zuehlke.reddit.util.ActivityUtils

/**
 * Created by chsc on 08.11.17.
 */

class LoginActivity : BaseActivtiy() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var loginFragment: LoginFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as LoginFragment?
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance()
            ActivityUtils.addFragmentToActivity(
                    supportFragmentManager, loginFragment, R.id.contentFrame)
        }
    }
}
