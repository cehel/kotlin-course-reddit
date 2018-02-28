package ch.gauch.parallel.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ch.zuehlke.pararrel.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by chsc on 12.09.17.
 *
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SessionViewModel::class)
    abstract fun bindSessionViewModel(sessionViewModel: SessionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}*/