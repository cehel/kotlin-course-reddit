package ch.zuehlke.reddit

import ch.zuehlke.reddit.features.login.FakeSharedPreferences
import ch.zuehlke.reddit.features.login.PreferencesHolder

/**
 * Created by chsc on 28.02.18.
 */
object Injection{

    fun provideSharedPreferencesHolder(): PreferencesHolder =
            PreferencesHolder(FakeSharedPreferences())
}