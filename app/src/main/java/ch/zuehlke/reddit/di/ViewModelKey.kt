package ch.zuehlke.reddit.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


/**
 * Created by celineheldner on 06.03.18.
 */

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)