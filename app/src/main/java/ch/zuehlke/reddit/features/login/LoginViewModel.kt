package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.common.base.Strings
import java.util.regex.Pattern


/**
 * Created by celineheldner on 28.02.18.
 * chapter_02_section_03_sealed_classes
 * TODO(Delete the enum [ViewState] and replace it by a implementation which is based on a Sealed class. The goal should be a [LoginViewModel] which is independent form the SharedPreferencesHolder)
 */
class LoginViewModel(private val preferencesHolder: PreferencesHolder): ViewModel(){

    private companion object {
        private const val KEY_USERNAME = "ch.zuehlke.reddit.features.login.key_username"
        private const val KEY_PASSWORD = "ch.zuehlke.reddit.features.login.key_password"
    }

    private val mutableViewState: MutableLiveData<ViewState> = MutableLiveData<ViewState>().apply { ViewState.NONE }
    val viewState : LiveData<ViewState> = mutableViewState

    private val emailPattern: Pattern = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    /*
    chapter_02_section_03_sealed_classes
    TODO(Delete this enum)
     */
    enum class ViewState{
        LOADING,NONE,INVALID_PASSWORD,INVALID_USERNAME,INVALID_CREDENTIALS,LOGGED_IN
    }

    fun login(userEmail: String, password: String) {
        mutableViewState.postValue(ViewState.LOADING)
        when{
            userEmail != "test.tester@test.com" && password != "123456" -> {  mutableViewState.postValue(ViewState.INVALID_CREDENTIALS)}
            userEmail != "test.tester@test.com" -> {  mutableViewState.postValue(ViewState.INVALID_USERNAME)}
            password != "123456" -> {  mutableViewState.postValue(ViewState.INVALID_PASSWORD)}
            else -> {
                preferencesHolder.putString(KEY_USERNAME,userEmail).commit()
                preferencesHolder.putString(KEY_PASSWORD,password).commit()
                mutableViewState.postValue(ViewState.LOGGED_IN)
            }
        }
    }

    /*
     chapter_02_section_03_sealed_classes
     TODO(This should not be needed anymore)
      */
    fun getLoginData(): Pair<String,String> {
        val username = preferencesHolder.getString(KEY_USERNAME,"")
        val password = preferencesHolder.getString(KEY_PASSWORD,"")
        return Pair(username,password)
    }

    fun verifyPasswordLength(password: String): Boolean =
            !Strings.isNullOrEmpty(password) && password.length >= 6

    fun isEmailValid(email: String): Boolean = emailPattern.matcher(email).matches()
}