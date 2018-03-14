package ch.zuehlke.reddit.features.news

import ch.zuehlke.reddit.R
import ch.zuehlke.reddit.di.scope.ActivityScope
import ch.zuehlke.reddit.di.scope.FragmentScope
import ch.zuehlke.reddit.features.BaseActivtiy
import ch.zuehlke.reddit.features.news.overview.OverviewFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

/**
 * Created by celineheldner on 14.03.18.
 */
/**
 * Created by celineheldner on 14.03.18.
 */
@Module
abstract class NewsActivityModule{

    @Binds
    abstract fun provideNewsActivity(newsActivity: NewsActivity) : BaseActivtiy

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeOverviewFragment(): OverviewFragment

    @Module
    companion object {

        @Provides
        @JvmStatic @Named("containerId")
        @ActivityScope
        fun provideFragmentId(): Int{
            return R.id.contentFrame
        }

        @Provides
        @JvmStatic
        @ActivityScope
        fun provideNavigationController(baseActivtiy: BaseActivtiy, @Named("containerId") containerId: Int): NavigationController {
            return NavigationController(baseActivtiy, containerId)
        }
    }


}