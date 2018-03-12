package ch.zuehlke.reddit.daggerexercise

import ch.zuehlke.reddit.daggerexercise.di.DaggerTestComponent
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Created by celineheldner on 12.03.18.
 */
class DaggerExerciseTest(){

    @Inject lateinit var mailBox: MailBox

    @Before
    fun setup(){
        DaggerTestComponent.builder().build().inject(this)
    }

    @Test
    fun testUserLogin(){
        val email = Email("Hello!","some@zuehlke.com")
        assert(mailBox.send(email))
    }
}