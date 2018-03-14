package ch.zuehlke.reddit.di

import ch.zuehlke.reddit.di.scope.FragmentScope
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
    abstract fun contributeOverviewFragment(): OverviewFragment
}