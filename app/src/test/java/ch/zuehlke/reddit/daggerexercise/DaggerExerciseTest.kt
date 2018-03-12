package ch.zuehlke.reddit.daggerexercise

import org.junit.Before
import org.junit.Test

/**
 * Created by celineheldner on 12.03.18.
 */
class DaggerExerciseTest{

    /*
    TODO:chapter_03_dagger_exercise1 Create a dagger graph so the dependencies below can be injected!

    Hint: Once the Component and Module exist, CleanProject and run the test, so Dagger can generate the classes
     */

    private val emailValidator = EmailValidator.getInstance()
    private val spamFilter = SpamFilter.getInstance()
    val mailBox = MailBox(emailValidator,spamFilter)

    @Before
    fun setup(){

    }

    @Test
    fun testUserLogin(){
        val email = Email("Hello!","some@zuehlke.com")
        assert(mailBox.send(email))
    }
}