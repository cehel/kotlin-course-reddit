package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.di.scope.FragmentScope
import ch.zuehlke.reddit.features.login.LoginFragment
import ch.zuehlke.reddit.features.news.detail.DetailFragment
import ch.zuehlke.reddit.features.news.detail.DetailFragmentModule
import ch.zuehlke.reddit.features.news.overview.OverviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by celineheldner on 07.03.18.
 */
@Module
public abstract class FragmentBuilderModule{

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = arrayOf(DetailFragmentModule::class))
    @FragmentScope
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeOverviewFragment(): OverviewFragment
}