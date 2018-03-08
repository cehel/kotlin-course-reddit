package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.common.base.Strings
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Created by celineheldner on 28.02.18.
 */
class LoginViewModel: ViewModel{

    private val mutableViewState: MutableLiveData<ViewState> = MutableLiveData<ViewState>().apply { ViewState.NONE }
    val viewState : LiveData<ViewState> = mutableViewState

    private val emailPattern: Pattern = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    enum class ViewState{
        LOADING,NONE,INVALID_PASSWORD,INVALID_USERNAME,INVALID_CREDENTIALS,LOGGED_IN
    }

    @Inject constructor(){

    }

    fun login(userEmail: String, password: String) {
        mutableViewState.postValue(ViewState.LOADING)
        when{
            userEmail != "test.tester@test.com" && password != "123456" -> {  mutableViewState.postValue(ViewState.INVALID_CREDENTIALS)}
            userEmail != "test.tester@test.com" -> {  mutableViewState.postValue(ViewState.INVALID_USERNAME)}
            password != "123456" -> {  mutableViewState.postValue(ViewState.INVALID_PASSWORD)}
            else -> {mutableViewState.postValue(ViewState.LOGGED_IN)}
        }
    }

    fun verifyPasswordLength(password: String): Boolean =
            !Strings.isNullOrEmpty(password) && password.length >= 6

    fun isEmailValid(email: String): Boolean = emailPattern.matcher(email).matches()
}