package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.zuehlke.reddit.BaseFragment
import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.di.Injectable
import ch.zuehlke.reddit.features.news.NewsActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


/**
 * Created by chsc on 08.11.17.
 */

class LoginFragment : BaseFragment(), Injectable {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
        private const val KEY_USERNAME = "ch.zuehlke.reddit.features.login.key_username"
        private const val KEY_PASSWORD = "ch.zuehlke.reddit.features.login.key_password"
    }

    @Inject
    lateinit var preferencesHolder: PreferencesHolder

    private var enteredUserName: String? by savedInstanceState()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_login, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loginViewModel = ViewModelProviders.of(activity).get(LoginViewModel::class.java)
        loginViewModel.viewState.observe(this, Observer { viewState: LoginState? -> handleViewState(viewState) })
        loginButton.setOnClickListener { loginViewModel.login(username.text.toString(), password.text.toString()) }
        val userName = preferencesHolder.getString(KEY_USERNAME, enteredUserName)
        val passWord = preferencesHolder.getString(KEY_PASSWORD,"")

        username.setText(userName)
        password.setText(passWord)

            username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Do nothing
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty() && loginViewModel.isEmailValid(editable.toString())) {
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

    fun handleViewState(viewState: LoginState?) {
        when(viewState){
            LoginState.WrongCredentials -> {
                usernameLayout.error = getString(R.string.login_screen_invalid_username)
                passwordLayout.error = getString(R.string.login_screen_invalid_password)}
            LoginState.WrongUserName -> usernameLayout.error = getString(R.string.login_screen_invalid_username)
            LoginState.WrongPassword -> passwordLayout.error = getString(R.string.login_screen_invalid_password)
            LoginState.Loading -> progressBar.visibility = View.VISIBLE
            is LoginState.LoggedIn -> {
                progressBar.visibility = View.GONE
                preferencesHolder.putString(KEY_USERNAME,viewState.username).commit()
                preferencesHolder.putString(KEY_PASSWORD,viewState.password).commit()
                val intent = Intent(context, NewsActivity::class.java)
                startActivity(intent)
                activity.finish()
            }
        }
    }


}
