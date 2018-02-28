package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by chsc on 28.02.18.
 */
class LoginViewModelFactory(private val preferencesHolder: PreferencesHolder): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
           return LoginViewModel(preferencesHolder) as T
       }
        throw IllegalArgumentException("Unkown Viewmodel class $modelClass cannot initiate it.")
    }

}