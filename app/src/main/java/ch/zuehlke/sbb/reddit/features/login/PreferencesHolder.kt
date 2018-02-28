package ch.zuehlke.sbb.reddit.features.login

import android.content.SharedPreferences


/**
 * Created by chsc on 28.02.18.
 */
class PreferencesHolder(private val sharedPreferences: SharedPreferences): SharedPreferences,SharedPreferences.Editor {

    override fun clear(): SharedPreferences.Editor = sharedPreferences.edit().clear()

    override fun putLong(p0: String?, p1: Long): SharedPreferences.Editor =
            sharedPreferences.edit().putLong(p0,p1)

    override fun putInt(p0: String?, p1: Int): SharedPreferences.Editor =
            sharedPreferences.edit().putInt(p0,p1)

    override fun remove(p0: String?): SharedPreferences.Editor = sharedPreferences.edit().remove(p0)

    override fun putBoolean(p0: String?, p1: Boolean): SharedPreferences.Editor =
            sharedPreferences.edit().putBoolean(p0,p1)

    override fun putStringSet(p0: String?, p1: MutableSet<String>?): SharedPreferences.Editor =
            sharedPreferences.edit().putStringSet(p0,p1)

    override fun commit(): Boolean = sharedPreferences.edit().commit()

    override fun putFloat(p0: String?, p1: Float): SharedPreferences.Editor =
            sharedPreferences.edit().putFloat(p0,p1)

    override fun apply() {
        sharedPreferences.edit().apply()
    }

    override fun putString(p0: String?, p1: String?): SharedPreferences.Editor =
            sharedPreferences.edit().putString(p0,p1)

    override fun contains(p0: String?): Boolean = sharedPreferences.contains(p0)

    override fun getBoolean(p0: String?, p1: Boolean): Boolean = sharedPreferences.getBoolean(p0,p1)

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
       sharedPreferences.unregisterOnSharedPreferenceChangeListener(p0)
    }

    override fun getInt(p0: String?, p1: Int): Int = sharedPreferences.getInt(p0,p1)

    override fun getAll(): MutableMap<String, *> = sharedPreferences.all

    override fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

    override fun getLong(p0: String?, p1: Long): Long = sharedPreferences.getLong(p0,p1)

    override fun getFloat(p0: String?, p1: Float): Float = getFloat(p0,p1)

    override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String> =
            sharedPreferences.getStringSet(p0,p1)

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) =
            sharedPreferences.registerOnSharedPreferenceChangeListener(p0)

    override fun getString(p0: String?, p1: String?): String = sharedPreferences.getString(p0,p1)

}