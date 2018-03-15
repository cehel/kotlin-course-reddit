package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.BaseTest
import dagger.Component
import javax.inject.Singleton

/**
 * Created by celineheldner on 09.03.18.
 */
@Singleton
@Component(modules = arrayOf(ViewModelModule::class, AppModuleTest::class))
interface AppComponentTest{

    fun inject(baseTest: BaseTest)

}