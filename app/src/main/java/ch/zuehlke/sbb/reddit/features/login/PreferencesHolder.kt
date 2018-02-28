package ch.zuehlke.sbb.reddit.features.login

import android.content.SharedPreferences


/**
 * Created by chsc on 28.02.18.
 */
class PreferencesHolder(private val sharedPreferences: SharedPreferences) : SharedPreferences by sharedPreferences, SharedPreferences.Editor by sharedPreferences.edit() {


}