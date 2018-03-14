package ch.zuehlke.reddit.features.login

import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.di.scope.ActivityScope
import ch.zuehlke.reddit.di.scope.FragmentScope
import ch.zuehlke.reddit.features.BaseActivtiy
import ch.zuehlke.reddit.features.news.NavigationController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

/**
 * Created by celineheldner on 14.03.18.
 */
@Module
abstract class LoginActivityModule{

    @Binds
    abstract fun provideLoginActivity(loginActivity: LoginActivity) : BaseActivtiy

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeLoginFragment(): LoginFragment

    @Module
    companion object {

        @Provides @JvmStatic @Named("containerId")
        @ActivityScope
        fun provideFragmentId(): Int{
            return R.id.contentFrame
        }

        @Provides @JvmStatic
        @ActivityScope
        fun provideNavigationController(baseActivtiy: BaseActivtiy, @Named containerId: Int): NavigationController {
            return NavigationController(baseActivtiy, containerId)
        }
    }


}