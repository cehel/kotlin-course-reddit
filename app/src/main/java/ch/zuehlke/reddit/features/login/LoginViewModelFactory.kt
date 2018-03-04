package ch.zuehlke.reddit.features.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by chsc on 28.02.18.
 *
 * chapter_02_section_03_sealed_classes
 * TODO("This class shouldn't be needed anymore if you implemented the sealed classes correctly, because the LoginViewModel should be independent form the [PreferencesHolder]")
 */
class LoginViewModelFactory(private val preferencesHolder: PreferencesHolder): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
           return LoginViewModel(preferencesHolder) as T
       }
        throw IllegalArgumentException("Unkown Viewmodel class $modelClass cannot initiate it.")
    }

}