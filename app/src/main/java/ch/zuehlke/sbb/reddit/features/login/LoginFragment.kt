package ch.zuehlke.sbb.reddit.features.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.sbb.reddit.R
import ch.zuehlke.sbb.reddit.features.news.NewsActivity
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * Created by chsc on 08.11.17.
 */

class LoginFragment : Fragment() {


    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_login, container, false)

        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loginViewModel = ViewModelProviders.of(activity).get(LoginViewModel::class.java)
        loginViewModel.viewState.observe(this, Observer { viewState: LoginViewModel.ViewState? -> handleViewState(viewState) })
        loginButton.setOnClickListener{loginViewModel.login(username.text.toString(), password.text.toString())}
        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty() && loginViewModel.verifyIsEmail(editable.toString())) {
                    usernameLayout.error = null
                } else {
                    usernameLayout.error = getString(R.string.login_screen_invalid_email)
                }
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (loginViewModel.verifyPasswordLength(editable.toString())) {
                    passwordLayout.error = null
                } else {
                    passwordLayout.error = getString(R.string.login_screen_invalid_password_length)
                }
            }
        })
    }

    fun handleViewState(viewState: LoginViewModel.ViewState?){
        when(viewState){
            LoginViewModel.ViewState.LOADING -> progressBar.visibility = View.VISIBLE
            LoginViewModel.ViewState.NONE -> progressBar.visibility = View.GONE
            LoginViewModel.ViewState.INVALID_PASSWORD -> usernameLayout.error = getString(R.string.login_screen_invalid_username)
            LoginViewModel.ViewState.INVALID_USERNAME -> passwordLayout.error = getString(R.string.login_screen_invalid_password)
            LoginViewModel.ViewState.INVALID_CREDENTIALS -> {
                usernameLayout.error = getString(R.string.login_screen_invalid_username)
                passwordLayout.error = getString(R.string.login_screen_invalid_password)
            }
            LoginViewModel.ViewState.LOGGED_IN -> {
                progressBar.visibility = View.GONE
                val intent = Intent(context, NewsActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }
    }


}
