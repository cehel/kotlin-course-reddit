package ch.zuehlke.reddit.common

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

/**
 * Created by chsc on 28.02.18.
 */

open class InstanceStateProvider<T> private constructor(private var bundle: Bundle) {

    private var cache: T? = null

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
        if (value == null) {
            bundle.remove(property.name)
        } else {
            when (value) {
                is Int -> {
                    bundle.putInt(property.name, value)
                }
                is Long -> {
                    bundle.putLong(property.name, value)
                }
                is Float -> {
                    bundle.putFloat(property.name, value)
                }
                is Double -> {
                    bundle.putDouble(property.name, value)
                }
                is String -> {
                    bundle.putString(property.name, value)
                }
                is Parcelable -> {
                    bundle.putParcelable(property.name, value)
                }
                is Serializable -> {
                    bundle.putSerializable(property.name, value)
                }
                is Bundle -> {
                    bundle.putBundle(property.name, value)
                }
                else -> throw IllegalArgumentException("Unkown property type for property ${property.name}")
            }
        }
    }

    protected fun getAndsetCache(key: String): T? =
            cache?: (bundle.get(key) as T?).apply { cache = this }

    class Nullable<T>(bundle: Bundle): InstanceStateProvider<T>(bundle){
        operator fun getValue(thisRef: Any?,property: KProperty<*>): T? = getAndsetCache(property.name)
    }

    class NonNullable<T>(bundle: Bundle,private val defaultValue: T): InstanceStateProvider<T>(bundle){
        operator fun getValue(thisRef: Any?,property: KProperty<*>): T? = getAndsetCache(property.name)?: defaultValue
    }
}