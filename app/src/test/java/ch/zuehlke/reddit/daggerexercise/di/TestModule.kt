package ch.zuehlke.reddit.daggerexercise.di

import ch.zuehlke.reddit.daggerexercise.EmailValidator
import ch.zuehlke.reddit.daggerexercise.MailBox
import ch.zuehlke.reddit.daggerexercise.SpamFilter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by celineheldner on 12.03.18.
 */
@Module
class TestModule{

    @Provides @Singleton
    fun provideSpamFilter() = SpamFilter()

    @Provides @Singleton
    fun provideEmailValidator() = EmailValidator()

    @Provides
    fun provideMailbox(spamFilter: SpamFilter, emailValidator: EmailValidator) = MailBox(emailValidator,spamFilter)

}