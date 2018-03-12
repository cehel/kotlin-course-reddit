package ch.zuehlke.reddit.daggerexercise.di

import ch.zuehlke.reddit.daggerexercise.DaggerExerciseTest
import dagger.Component
import javax.inject.Singleton

/**
 * Created by celineheldner on 12.03.18.
 */
@Singleton
@Component(modules = arrayOf(TestModule::class))
interface TestComponent{

    fun inject(test: DaggerExerciseTest)
}