package ch.zuehlke.reddit.common

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

/**
 * Created by chsc on 28.02.18.
 *
 * chapter_02_section_01_property_delegates_exercise
 */

open class InstanceStateProvider<T> private constructor(private var bundle: Bundle) {


    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        //TODO("Implement the persistence logic to save different kind of objects (Int, Double, Long, Serializable,...)")
    }


    /*
      TODO("Provide a delegate which can return a nullable value, read from the provided bundle")
      For example: Nullabel(bundle: Bundle): InstanceProvider<T>(bundle)
     */


    /*
      TODO("Provide a second delegate which also reads the value from the provided bundle, but doesn't allow nullable types.)
     */
}