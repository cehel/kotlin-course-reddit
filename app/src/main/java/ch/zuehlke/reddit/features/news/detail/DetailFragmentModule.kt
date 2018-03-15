package ch.zuehlke.reddit.features.news.detail

import android.app.Fragment
import android.content.Context
import android.support.v7.app.AppCompatActivity
import ch.zuehlke.reddit.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * Created by celineheldner on 15.03.18.
 */
@Module
class DetailFragmentModule{

    @Provides
    @FragmentScope
    fun provideDetailAdapter(context: Context) = DetailAdapter(context)
}