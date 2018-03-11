package ch.zuehlke.reddit.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by celineheldner on 06.03.18.
 */

/*
TODO:chapter_03_dagger_exercise2
In order to automatically create a ViewModel without taking care of dependencies and creating a specific ViewModelProvider.Factory,
we create this generic one, which can be used for any Type extending ViewModel.

Using Dagger: Provide the map Injected as a constructor parameter below: It is a map with Key of type: Class that extends a ViewModel
and value: a Provider of ViewModel

1. Create a ViewModelModule which puts the existing ViewModels into a Map
2. also Provide the ViewModelFactory given in this file
3. Replace all occurences of the default ViewModelProvider.Factory with this one
 */

@Singleton
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]
        //if not the exact class, but a subclass is in the map
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) throw IllegalArgumentException("unknown model class " + modelClass)

        //Return the ViewModel
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}