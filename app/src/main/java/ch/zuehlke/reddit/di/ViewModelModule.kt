package ch.zuehlke.reddit.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ch.zuehlke.reddit.features.login.LoginViewModel
import ch.zuehlke.reddit.features.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by celineheldner on 06.03.18.
 */
@Module
internal abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(newsViewModel: NewsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

}