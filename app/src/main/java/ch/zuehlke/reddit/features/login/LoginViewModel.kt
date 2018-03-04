package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.common.base.Strings
import java.util.regex.Pattern


/**
 * Created by celineheldner on 28.02.18.
 */
class LoginViewModel(): ViewModel(){

    private val mutableViewState: MutableLiveData<LoginState> = MutableLiveData<LoginState>()
    val viewState : LiveData<LoginState> = mutableViewState

    private val emailPattern: Pattern = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")


    fun login(userEmail: String, password: String) {
        mutableViewState.postValue(LoginState.Loading)
        when{
            userEmail != "test.tester@test.com" && password != "123456" -> {  mutableViewState.postValue(LoginState.WrongCredentials)}
            userEmail != "test.tester@test.com" -> {  mutableViewState.postValue(LoginState.WrongUserName)}
            password != "123456" -> {  mutableViewState.postValue(LoginState.WrongPassword)}
            else -> {
                val loggedIn = LoginState.LoggedIn(userEmail, password)
                mutableViewState.postValue(loggedIn)
            }
        }
    }

    fun verifyPasswordLength(password: String): Boolean =
            !Strings.isNullOrEmpty(password) && password.length >= 6

    fun isEmailValid(email: String): Boolean = emailPattern.matcher(email).matches()
}