package ch.zuehlke.reddit.features.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import ch.zuehlke.reddit.BaseTest
import ch.zuehlke.reddit.RXRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Scheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import javax.inject.Inject
import javax.inject.Named
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins




/**
 * Created by celineheldner on 09.03.18.
 */
class LoginViewModelTest: BaseTest(){

    @Mock lateinit var observer: Observer<LoginState>

    @get:Rule
    var rule:TestRule = InstantTaskExecutorRule()





    override fun setup(){
        super.setup()



        this.observer = mock()
        loginViewModel.viewState.observeForever(observer)




    }

    @Test
    fun testWrongCredentialsLogin(){

        loginViewModel.login("wronguser","short")

        verify(observer).onChanged(LoginState.WrongCredentials)
    }

    @Test
    fun testWrongPassword(){

        loginViewModel.login("test.tester@test.com","short")

        verify(observer).onChanged(LoginState.WrongPassword)
    }

    @Test
    fun testWrongUser(){

        loginViewModel.login("wronguser","123456")

        verify(observer).onChanged(LoginState.WrongUserName)
    }

    @Test
    fun correctCredentials(){

        loginViewModel.login("test.tester@test.com","123456")

        verify(observer).onChanged(LoginState.Loading)
    }
}