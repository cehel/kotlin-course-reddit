package ch.zuehlke.sbb.reddit.features.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.common.base.Strings


/**
 * Created by celineheldner on 28.02.18.
 */
class LoginViewModel: ViewModel(){

    private val mutableViewState: MutableLiveData<ViewState> = MutableLiveData<ViewState>().apply { ViewState.NONE }
    val viewState : LiveData<ViewState> = mutableViewState

    enum class ViewState{
        LOADING,NONE,INVALID_PASSWORD,INVALID_USERNAME,INVALID_CREDENTIALS,LOGGED_IN
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

    fun verifyPasswordLength(password: String): Boolean {
        return !Strings.isNullOrEmpty(password) && password.length >= 6
    }

    fun verifyIsEmail(email: String): Boolean {
        val matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(email)
        return matcher.matches()
    }
}