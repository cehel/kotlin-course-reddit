package ch.gauch.parallel.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey

import kotlin.reflect.KClass

/**
 * Created by chsc on 12.09.17.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
